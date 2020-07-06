package edu.calc.becas.mseguridad.login.api;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.login.model.DataLogin;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.login.service.LoginService;
import edu.calc.becas.mseguridad.menu.service.MenuService;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import edu.calc.becas.mvc.config.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone Servicios de autenticacion de usuarios
 */
@RestController
@RequestMapping("/login")
@Api(tags = "login-api", description = "Servicios de autenticaci\u00f3n")
public class LoginAPI {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MenuService menuService;

    public LoginAPI(LoginService loginService, JwtTokenProvider jwtTokenProvider, MenuService menuService) {
        this.loginService = loginService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.menuService = menuService;
    }


    /**
     * @param user: Autentica al usuario en la aplicacion
     * @return
     * @throws InvalidJwtAuthenticationException
     * @throws ConnectionJdbcException
     * @throws GenericException
     */
    @PostMapping
    @ApiOperation(value = "Inicia sesi\u00f3n en la aplicaci\u00f3n")
    public UserLogin login(@ApiParam(value = "usuario") @RequestBody DataLogin user) throws InvalidJwtAuthenticationException, ConnectionJdbcException, GenericException {

        UserLogin dataUserLogin = new UserLogin();
        dataUserLogin.setUsername(user.getUsername());
        dataUserLogin.setPassword(user.getPassword());

        UserLogin userLogin = loginService.login(dataUserLogin);
        userLogin.setPassword(null);
        userLogin.setToken(this.jwtTokenProvider.createToken(userLogin));
        userLogin.setMenu(menuService.getMenu(userLogin.getUsername()));
        return userLogin;
    }
}
