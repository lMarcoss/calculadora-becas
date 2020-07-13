package edu.calc.becas.mseguridad.rolesypermisos.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mseguridad.rolesypermisos.service.RolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * API para exponer servicios de administracion de roles de usuario
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@RestController
@RequestMapping("/roles")
@Api(description = "Servicios para administraci\u00f3n de seguridad de la aplicaci\u00f3n")
public class RolAPI {

    private final RolService rolService;

    @Autowired
    public RolAPI(RolService rolService) {
        this.rolService = rolService;
    }


    /**
     * Servicio para obtener el listado de usuarios por paginacion y estatus
     *
     * @param page     pagina a recuperar
     * @param pageSize registros a recuperar por pagina
     * @param status   estatus de los registros a obtener
     * @return lista de usuarios
     */
    @GetMapping
    @ApiOperation(value = "Obtiene el listado de roles")
    public WrapperData getAllRoles(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS) @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status) {
        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return this.rolService.getAllByStatus(Integer.parseInt(page), Integer.parseInt(pageSize), status);
    }
}
