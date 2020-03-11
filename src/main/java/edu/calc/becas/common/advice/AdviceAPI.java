package edu.calc.becas.common.advice;

import edu.calc.becas.mvc.config.security.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: API para reporte de errores
 * Date: 07/03/20
 */
@RestControllerAdvice
public class AdviceAPI {

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String comunicationFailureConnectionBD(InvalidJwtAuthenticationException e) {
        return e.getMessage();
    }
}
