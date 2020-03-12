package edu.calc.becas.exceptions;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Exception jdbc
 * Date: 11/03/20
 */
public class ConnectionJdbcException extends Throwable {
    public ConnectionJdbcException(String message, Throwable cause) {
        super(message, cause);

    }
}
