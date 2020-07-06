package edu.calc.becas.welcome;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 11/03/20
 */
@RestController
@RequestMapping("/welcome")
@Api(description = "Servicio para validar estatus de la aplicaci\u00f3n")
public class WelcomeAPI {

    @GetMapping()
    @ApiOperation(value = "Indica que la aplicaci\u00f3n est\u00e1 en l\u00ednea")
    public String welcome() {
        return "Bienvenido";
    }
}
