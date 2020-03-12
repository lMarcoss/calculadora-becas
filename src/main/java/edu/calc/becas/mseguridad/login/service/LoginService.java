package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;

public interface LoginService {

    UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException;
}
