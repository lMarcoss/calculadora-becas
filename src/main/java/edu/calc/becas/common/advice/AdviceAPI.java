package edu.calc.becas.common.advice;

import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: API para reporte de errores
 * Date: 07/03/20
 */
@RestControllerAdvice
public class AdviceAPI {

    /*@ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ComunicationFailure comunicationFailureConnectionBD(Exception e) {

        return new ComunicationFailure(null, 0, e.getMessage(), "La base de datos no est\u00e1 disponible", e.getMessage(), null);
    }*/
}
