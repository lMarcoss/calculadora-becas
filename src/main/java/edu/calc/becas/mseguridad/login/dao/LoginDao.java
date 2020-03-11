package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;

public interface LoginDao {

    UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException;
}
