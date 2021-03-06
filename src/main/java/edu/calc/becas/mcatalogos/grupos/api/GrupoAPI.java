package edu.calc.becas.mcatalogos.grupos.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.grupos.service.GrupoService;
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
 * API para exponer las consultas de grupos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
@RestController
@RequestMapping("/grupos")
@Api(description = "Servicios para administraci\u00f3n de grupos")
public class GrupoAPI {

    private final GrupoService grupoService;

    @Autowired
    public GrupoAPI(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    /**
     * Recupera todos los grupos o de una licenciatura
     *
     * @param page         pagina
     * @param pageSize     registros por pagina
     * @param status       estatus
     * @param licenciatura licenciatura
     * @return grupos
     * @throws GenericException error de consulta
     */
    @GetMapping
    @ApiOperation(value = "Obtiene el listado de grupos")
    public WrapperData<Grupo> getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS) @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,
            @ApiParam(value = "Licenciatura de los registros a recuperar", defaultValue = LICENCIATURA_DEFAULT) @RequestParam(value = "licenciatura", defaultValue = LICENCIATURA_DEFAULT, required = false) String licenciatura
    ) throws GenericException {
        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return grupoService.getAllByStatusAndOneParam(Integer.parseInt(page), Integer.parseInt(pageSize), status, licenciatura);
    }

}
