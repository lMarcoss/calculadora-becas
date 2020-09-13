package edu.calc.becas.mseguridad.login.service;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mseguridad.login.dao.LoginDaoImpl;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.menu.service.MenuService;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import edu.calc.becas.mvc.config.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

/**
 * Implementacion de servicios para autenticacion de usuarios
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final LoginDaoImpl loginAPIDaoImpl;
    private final CicloEscolarService cicloEscolarService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MenuService menuService;

    public LoginServiceImpl(LoginDaoImpl loginAPIDaoImpl, CicloEscolarService cicloEscolarService, JwtTokenProvider jwtTokenProvider, MenuService menuService) {
        this.loginAPIDaoImpl = loginAPIDaoImpl;
        this.cicloEscolarService = cicloEscolarService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.menuService = menuService;
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


        userLogin.setPassword(null);
        userLogin.setToken(this.jwtTokenProvider.createToken(userLogin));
        userLogin.setMenu(menuService.getMenu(userLogin.getUsername()));

        return userLogin;
    }
}
