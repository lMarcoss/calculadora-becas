package edu.calc.becas.mseguridad.menu.api;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;
import edu.calc.becas.mseguridad.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(description = "Servicios para administración de menú")
public class MenuAPI {

    private final MenuService menuService;

    public MenuAPI(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @ApiOperation(value = "Obtiene el listado de menú para un usuario")
    public List<Menu> getMenu(@ApiParam(value = "Usuario en sesión", defaultValue = "0") @RequestParam(value = "username", defaultValue = "0") String username) throws GenericException {
        return menuService.getMenu(username);
    }
}
