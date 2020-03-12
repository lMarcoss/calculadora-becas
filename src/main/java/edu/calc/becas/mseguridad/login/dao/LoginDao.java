package edu.calc.becas.mseguridad.login.dao;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.dao.EmptyResultDataAccessException;

public interface LoginDao {

    UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException, EmptyResultDataAccessException;
}
