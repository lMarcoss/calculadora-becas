package edu.calc.becas.mconfiguracion.login.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

import static edu.calc.becas.mconfiguracion.login.dao.QueriesLogin.QRY_GET_INFO_LOGIN;

@Repository
public class LoginAPIDaoImpl extends BaseDao implements  LoginAPIDao{

  public LoginAPIDaoImpl(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  public Usuario login(String username, String password){
    return jdbcTemplate.queryForObject(QRY_GET_INFO_LOGIN,
      new Object[]{ username, username},
      (((rs, i) -> mapperLoginUser(rs))));
  }

  private Usuario mapperLoginUser(ResultSet rs) throws SQLException {
    Usuario usuario = new Usuario();
    Rol rol = new Rol();
    usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
    usuario.setNombres(rs.getString("NOMBRES"));
    usuario.setApePaterno(rs.getString("APE_PATERNO"));
    usuario.setApeMaterno(rs.getString("APE_MATERNO"));
    usuario.setTipoUsuario(rs.getString("ID_ROL"));
    usuario.setUsername(rs.getString("USERNAME"));
    rol.setIdRol(rs.getInt("ID_ROL"));
    rol.setNombre(rs.getString("ROL"));
    usuario.setRol(rol);
    return usuario;
  }
}
