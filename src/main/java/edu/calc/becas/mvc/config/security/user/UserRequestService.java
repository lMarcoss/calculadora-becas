package edu.calc.becas.mvc.config.security.user;

import edu.calc.becas.mseguridad.login.model.UserLogin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 11/03/20
 */
public interface UserRequestService {
    UserLogin getUserLogin(HttpServletRequest request);
}
