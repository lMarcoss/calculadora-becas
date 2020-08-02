package edu.calc.becas.common.advice;

import edu.calc.becas.exceptions.ConnectionJdbcException;
import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * API para reporte de errores
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 07/03/20
 */
@RestControllerAdvice
public class AdviceAPI {

    /**
     * Notifica que el usuario no esta autorizado para consumir los servicios
     *
     * @param e error
     * @return error de autorizacion
     */
    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String comunicationFailureConnectionBD(InvalidJwtAuthenticationException e) {
        return e.getMessage();
    }

    /**
     * Notifica al usuario si hay falla en la  comunicacion con la BD
     *
     * @param e error
     * @return error de comunicacion con la BD
     */
    @ExceptionHandler(ConnectionJdbcException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public String comunicationFailureConnectionBD(ConnectionJdbcException e) {
        return e.getMessage();
    }
}
