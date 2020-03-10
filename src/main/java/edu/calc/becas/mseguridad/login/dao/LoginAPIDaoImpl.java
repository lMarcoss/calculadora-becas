package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.calc.becas.mseguridad.login.dao.QueriesLogin.QRY_GET_INFO_LOGIN;

@Repository
public class LoginAPIDaoImpl extends BaseDao implements LoginAPIDao {

    private final MessageApplicationProperty messageApplicationProperty;

    public LoginAPIDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate);
        this.messageApplicationProperty = messageApplicationProperty;
    }

    public Usuario login(String username, String password) throws GenericException {
        try {
            return jdbcTemplate.queryForObject(QRY_GET_INFO_LOGIN,
                    new Object[]{username, username},
                    (((rs, i) -> mapperLoginUser(rs))));
        } catch (EmptyResultDataAccessException e) {
            throw new GenericException(messageApplicationProperty.getUsuarioNoRegistrado());
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
        rol.setIdRol(rs.getInt("ID_ROL"));
        rol.setNombre(rs.getString("ROL"));
        usuario.setRol(rol);
        return usuario;
    }
}
