package edu.calc.becas.mseguridad.login.api;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.exceptions.GenericException;
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

@RestController
@RequestMapping("/login")
@Api(description = "Servicios para realizar el login de la aplicación")
public class LoginAPI {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MenuService menuService;

    public LoginAPI(LoginService loginService, JwtTokenProvider jwtTokenProvider, MenuService menuService) {
        this.loginService = loginService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.menuService = menuService;
    }


    @PostMapping
    @ApiOperation(value = "Realiza el login del usuario validando el usuario y contraseña")
    public UserLogin login(@ApiParam(value = "usuario") @RequestBody UserLogin user) throws InvalidJwtAuthenticationException, ConnectionJdbcException, GenericException {
        UserLogin userLogin = loginService.login(user);
        userLogin.setPassword(null);
        userLogin.setToken(this.jwtTokenProvider.createToken(userLogin));
        userLogin.setMenu(menuService.getMenu(userLogin.getUsername()));
        return userLogin;
    }
}
