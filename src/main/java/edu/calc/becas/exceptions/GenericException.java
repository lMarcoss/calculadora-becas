package edu.calc.becas.exceptions;

/**
 * Exception generica a lanzar al ocurrir error en los servicios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 5/1/19
 */
public class GenericException extends Exception {
    public GenericException(String message) {
        super(message);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(Throwable cause, String message) {
        super(message, cause);
    }
}
