package edu.calc.becas.mseguridad.menu.api;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;
import edu.calc.becas.mseguridad.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API para exponer servicios de administracion de menus
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 */
@RestController
@RequestMapping("/menu")
@Api(description = "Servicios para administraci\u00f3n de men\u00fa")
public class MenuAPI {

    private final MenuService menuService;

    public MenuAPI(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Consulta de menu por usuario
     *
     * @param username usuario
     * @return menu del usuario
     * @throws GenericException
     */
    @GetMapping
    @ApiOperation(value = "Obtiene el listado de men\u00fa para un usuario")
    public List<Menu> getMenu(@ApiParam(value = "Usuario en sesi\u00f3n", defaultValue = "0") @RequestParam(value = "username", defaultValue = "0") String username) throws GenericException {
        return menuService.getMenu(username);
    }
}
