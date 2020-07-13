package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mseguridad.login.dao.LoginDaoImpl;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Implementacion de servicios para autenticacion de usuarios
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDaoImpl loginAPIDaoImpl;
    private final CicloEscolarService cicloEscolarService;

    public LoginServiceImpl(LoginDaoImpl loginAPIDaoImpl, CicloEscolarService cicloEscolarService) {
        this.loginAPIDaoImpl = loginAPIDaoImpl;
        this.cicloEscolarService = cicloEscolarService;
    }

    /**
     * Autentica un usuario
     *
     * @param usuario usuario
     * @return usuario autenticado
     * @throws InvalidJwtAuthenticationException
     * @throws ConnectionJdbcException
     * @throws GenericException
     */
    @Override
    public UserLogin login(UserLogin usuario) throws InvalidJwtAuthenticationException, ConnectionJdbcException, GenericException {
        UserLogin userLogin = loginAPIDaoImpl.login(usuario);

        userLogin.setPeriodoActual(cicloEscolarService.getCicloEscolarActual());

        return userLogin;
    }
}
