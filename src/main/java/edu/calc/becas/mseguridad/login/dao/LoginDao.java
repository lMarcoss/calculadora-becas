package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Define los repositorios para validar autenticacion de usuarios con la BD
 */
public interface LoginDao {
    /**
     * Valida usuario de login con la BD
     *
     * @param usuario usuario a validar
     * @return usuario autenticado
     * @throws InvalidJwtAuthenticationException
     * @throws ConnectionJdbcException
     * @throws EmptyResultDataAccessException
     */
    UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException, EmptyResultDataAccessException;
}
