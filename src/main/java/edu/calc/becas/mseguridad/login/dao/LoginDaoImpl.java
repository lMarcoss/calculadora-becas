package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.utils.UtilMethods;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.calc.becas.mseguridad.login.dao.QueriesLogin.QRY_VALIDA_ALUMNO;
import static edu.calc.becas.mseguridad.login.dao.QueriesLogin.QRY_VALIDA_USUARIO;

@Repository
@Slf4j
public class LoginDaoImpl extends BaseDao implements LoginDao {

    @Value("${prop.security.secret.key.start}")
    private String secretKeyStart;

    @Value("${prop.security.secret.key.end}")
    private String secretKeyEnd;

    public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException {
        try {
            if (!UtilMethods.isBlank(usuario.getMatricula())) {
                //alumno
                usuario.setPassword("");
                Integer count = jdbcTemplate.queryForObject(QRY_VALIDA_ALUMNO,
                        new Object[]{usuario.getMatricula().trim()},
                        Integer.class);
                if (count != null && count > 0) {
                    usuario.setPassword("");
                } else {
                    throw new Exception("Usuario no encontrado o no est\u00e1 activo");
                }
                usuario.setUsername(null);
                usuario.setRol("ALUMNO");
                return usuario;
            } else {
                //usuario
                usuario.setMatricula(null);
                String rol = jdbcTemplate.queryForObject(QRY_VALIDA_USUARIO,
                        new Object[]{usuario.getUsername().trim(), secretKeyStart, usuario.getPassword(), secretKeyEnd},
                        String.class);

                usuario.setPassword("");
                usuario.setRol(rol);
                return usuario;


            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InvalidJwtAuthenticationException("Usuario no autorizado");
        }
    }


    private Usuario mapperLoginUser(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setApePaterno(rs.getString("APE_PATERNO"));
        usuario.setApeMaterno(rs.getString("APE_MATERNO"));
        usuario.setUsername(rs.getString("USERNAME"));
        usuario.setCommonVal01(rs.getString("COMMONVAL01"));
        usuario.setCommonVal02(rs.getString("COMMONVAL02"));
        usuario.setCommonVal03(rs.getString("COMMONVAL03"));
        rol.setIdRol(rs.getInt("ID_ROL"));
        rol.setNombre(rs.getString("ROL"));
        usuario.setRol(rol);
        return usuario;
    }
}
