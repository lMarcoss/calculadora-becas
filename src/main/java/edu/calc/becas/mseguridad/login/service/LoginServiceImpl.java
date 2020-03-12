package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mseguridad.login.dao.LoginDaoImpl;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDaoImpl loginAPIDaoImpl;

    public LoginServiceImpl(LoginDaoImpl loginAPIDaoImpl) {
        this.loginAPIDaoImpl = loginAPIDaoImpl;
    }

    @Override
    public UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException {
        return loginAPIDaoImpl.login(usuario);
    }
}
