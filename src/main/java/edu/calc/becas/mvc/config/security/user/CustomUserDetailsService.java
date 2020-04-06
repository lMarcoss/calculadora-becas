package edu.calc.becas.mvc.config.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 4/16/19
 */
@Service("userCustomDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCustomDetails userCustomDetails = new UserCustomDetails();
        userCustomDetails.setUsername(username);

        return userCustomDetails;
    }
}
