package edu.calc.becas.mcatalogos.licenciaturas.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.service.LicenciaturaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * API para exponer  licenciaturas
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * Date: 3/23/19
 */

@RestController
@RequestMapping("/licenciaturas")
@Api(description = "Servicios para administraci\u00f3n de licienciaturas")
public class LicenciaturaAPI {

    private final LicenciaturaService licenciaturaService;

    @Autowired
    public LicenciaturaAPI(LicenciaturaService licenciaturaService) {
        this.licenciaturaService = licenciaturaService;
    }

    /**
     * Obtiene el listado de licenciaturas
     *
     * @param page     pagina
     * @param pageSize registros por pagina
     * @param status   estatus
     * @return licenciaturas
     * @throws GenericException error de consulta
     */
    @GetMapping
    @ApiOperation(value = "Obtiene el listado de licenciaturas")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS) @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status
    ) throws GenericException {

        return licenciaturaService.getAll();
    }

    /**
     * Obtiene los datos de una licenciatua por clave
     *
     * @param claveLicenciatura clave
     * @return licenciatura
     * @throws GenericException error de consulta
     */
    @GetMapping("/detalle-licenciatura/clave/{clave-licenciatura}")
    @ApiOperation(value = "Obtiene detalle de la licenciatura por clave")
    public Licenciatura getDetailByClave(
            @ApiParam(value = "clave-licenciatura", required = true) @PathVariable("clave-licenciatura") String claveLicenciatura
    ) throws GenericException {

        return licenciaturaService.getDetailByClave(claveLicenciatura);
    }

}

