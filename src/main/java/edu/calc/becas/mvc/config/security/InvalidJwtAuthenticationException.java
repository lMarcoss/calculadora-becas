package edu.calc.becas.mvc.config.security;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * Description:
 * Date: 4/16/19
 */
public class InvalidJwtAuthenticationException extends Throwable {
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
