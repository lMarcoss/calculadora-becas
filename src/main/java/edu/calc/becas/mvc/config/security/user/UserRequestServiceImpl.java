package edu.calc.becas.mvc.config.security.user;

import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Servicio para exponer usuario del token
 * Date: 11/03/20
 */
@Service
public class UserRequestServiceImpl implements UserRequestService {

    private final JwtTokenProvider jwtTokenProvider;

    public UserRequestServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserLogin getUserLogin(HttpServletRequest request) {
        return jwtTokenProvider.getUserByToken(request);
    }
}
