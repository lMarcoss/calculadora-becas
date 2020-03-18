package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.calc.becas.mseguridad.login.dao.QueriesLogin.*;

@Repository
@Slf4j
public class LoginDaoImpl extends BaseDao implements LoginDao {

    @Value("${prop.security.secret.key.start}")
    private String secretKeyStart;

    @Value("${prop.security.secret.key.end}")
    private String secretKeyEnd;

    public LoginDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    public UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException, EmptyResultDataAccessException {

        try {

            //alumno
            Integer count = null;
            try {
                count = jdbcTemplate.queryForObject(QRY_VALIDA_ALUMNO,
                        new Object[]{usuario.getUsername()},
                        Integer.class);
                if(count == null || count == 0){
                    throw new EmptyResultDataAccessException(0);
                }


                usuario.setRol("ALUMNO");

                if(!usuario.getUsername().trim().equalsIgnoreCase(usuario.getPassword())){
                    throw new EmptyResultDataAccessException(0);
                }
                Usuario usuarioAlumno = null;
                try {
                    usuarioAlumno = jdbcTemplate.queryForObject(QRY_GET_INFO_ALUMNO,
                            new Object[]{usuario.getUsername()},
                            (((rs, i) -> mapperAlumnoLogin(rs))));

                    usuario.setUsuarioAlumno(usuarioAlumno);
                    usuario.setEsAlumno(true);


                    if (usuarioAlumno != null) {
                        usuario.setNombreUsuario(usuarioAlumno.getNombres());
                        usuario.setApellidoPaterno(usuarioAlumno.getApePaterno());
                        usuario.setApellidoMaterno(usuarioAlumno.getApeMaterno());
                    }
                    return usuario;

                } catch (EmptyResultDataAccessException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                    throw new InvalidJwtAuthenticationException("Error al consultar datos del alumno");
                }


            } catch (EmptyResultDataAccessException e) {
                log.error(ExceptionUtils.getStackTrace(e));

                // valida si es usuario normal

                //usuario
                usuario.setEsAlumno(false);
                try {
                    Usuario usuarioLogin = jdbcTemplate.queryForObject(QRY_VALIDA_USUARIO,
                            new Object[]{usuario.getUsername().trim(), secretKeyStart, usuario.getPassword(), secretKeyEnd},
                            (((rs, i) -> mapperUsuarioLogin(rs))));
                    if (usuarioLogin != null) {
                        usuario.setRol(usuarioLogin.getRol().getNombre());
                        usuario.setNombreUsuario(usuarioLogin.getNombres());
                        usuario.setApellidoPaterno(usuarioLogin.getApePaterno());
                        usuario.setApellidoMaterno(usuarioLogin.getApeMaterno());
                    }

                    return usuario;

                } catch (EmptyResultDataAccessException ex) {
                    log.error(ExceptionUtils.getStackTrace(ex));
                    throw new InvalidJwtAuthenticationException("Usuario o contrase\u00f1a incorrecta");
                }
            }


        } catch (CannotGetJdbcConnectionException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new ConnectionJdbcException("Error de conexi\u00f3n a la Base de Datos", e);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new InvalidJwtAuthenticationException("Usuario no autorizado");
        }
    }

    private Usuario mapperUsuarioLogin(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();

        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setApePaterno(rs.getString("APE_PATERNO"));
        usuario.setApeMaterno(rs.getString("APE_MATERNO"));

        rol.setNombre(rs.getString("NOMBRE_ROL"));
        usuario.setRol(rol);
        return usuario;
    }


    private Usuario mapperAlumnoLogin(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        usuario.setIdUsuario(rs.getInt("ID_ALUMNO"));
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
