package edu.calc.becas.mcatalogos.defpercentbeca.api;

import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mcatalogos.defpercentbeca.service.DefPorcentajeActividadService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: api para exponer servicios de consulta de definicion de porcentajes de beca por porcentaje actividad
 * Date: 03/05/20
 */
@RestController
@RequestMapping("/def-porcentaje-beca")
@Api(description = "Porcentajes de becas por porcentaje de actividad", tags = "API Porcentaje beca actividad")
public class DefPercentBecaByActivityAPI {

    private DefPorcentajeActividadService defPorcentajeActividadService;

    public DefPercentBecaByActivityAPI(DefPorcentajeActividadService defPorcentajeActividadService) {
        this.defPorcentajeActividadService = defPorcentajeActividadService;
    }

    @GetMapping("/periodo-actual")
    public DefPorcentajeActividad getDefPorcentajeActividades() {
        return defPorcentajeActividadService.getDefPorcentajeActividades();
    }
}
