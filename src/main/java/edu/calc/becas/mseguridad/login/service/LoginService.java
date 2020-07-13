package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;

/**
 * Define servicios de login - autenticacion de usuario
 */
public interface LoginService {
    /**
     * Valida autenticacion de usuario
     *
     * @param usuario usuario
     * @return usuario autenticado
     * @throws InvalidJwtAuthenticationException
     * @throws ConnectionJdbcException
     * @throws GenericException
     */
    UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException, GenericException;
}
