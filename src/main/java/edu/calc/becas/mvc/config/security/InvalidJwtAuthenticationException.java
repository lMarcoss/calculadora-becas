package edu.calc.becas.mvc.config.security;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 4/16/19
 */
public class InvalidJwtAuthenticationException extends Throwable {
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
