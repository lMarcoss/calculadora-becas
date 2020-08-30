package edu.calc.becas.cache;

import edu.calc.becas.cache.service.CatalogosSystemaHorarios;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API para exponer servicios de actualizacion de la lista de grupos, licenciaturas y periodo
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 05/06/20
 */
@RestController
@RequestMapping("/recarga-datos-sistema-horario")
@Api(description = "Servicios para recargar la informaci\u00f3n del sistema de horarios")
public class CacheAPI {


    private final CatalogosSystemaHorarios catalogosSystemaHorarios;

    public CacheAPI(CatalogosSystemaHorarios catalogosSystemaHorarios) {
        this.catalogosSystemaHorarios = catalogosSystemaHorarios;
    }

    /**
     * Actualiza las licenciaturas con las licenciaturas que se tiene en el sistema de  horarios
     *
     * @return si es exitoso o no
     * @throws GenericException error al realizar la consulta
     */
    @GetMapping(value = "/")
    @ApiOperation(value = "Recarga periodo, licenciaturas y grupos")
    public String reloadAllDataFromScheduledSystem() throws GenericException {
        DataScheduleSystem.C_CONSTANT_DATA.clear();
        CicloEscolarVo cicloEscolarVo = catalogosSystemaHorarios.getCicloEscolarActualFromScheduledSystem();
        catalogosSystemaHorarios.getAllFromScheduledSystem();
        catalogosSystemaHorarios.getAllAllFromScheduledSystem(cicloEscolarVo);
        return "Recarga de datos terminado";
    }
}
