package edu.calc.becas.cache;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.service.GrupoService;
import edu.calc.becas.mcatalogos.licenciaturas.service.LicenciaturaService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 05/06/20
 */
@RestController
@RequestMapping("/recarga-datos-sistema-horario")
@Api(description = "Servicios para recargar la información del sistema de horarios")
public class CacheAPI {

    private final CicloEscolarService cicloEscolarService;

    private final GrupoService grupoService;

    private final LicenciaturaService licenciaturaService;

    public CacheAPI(CicloEscolarService cicloEscolarService, GrupoService grupoService, LicenciaturaService licenciaturaService) {
        this.cicloEscolarService = cicloEscolarService;
        this.grupoService = grupoService;
        this.licenciaturaService = licenciaturaService;
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "Recarga periodo, licenciaturas y grupos")
    public String reloadAllDataFromScheduledSystem() throws GenericException {
        DataScheduleSystem.C_CONSTANT_DATA.clear();
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActualFromScheduledSystem();
        licenciaturaService.getAllFromScheduledSystem();
        grupoService.getAllAllFromScheduledSystem(cicloEscolarVo);
        return "¡Recarga de datos terminado!";
    }
}
