package edu.calc.becas.reporte.percent.beca.api;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.common.utils.UtilMethods;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import edu.calc.becas.reporte.percent.beca.model.ReporteActividad;
import edu.calc.becas.reporte.percent.beca.service.ReportPercentBecaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
@RestController
@RequestMapping("/reporte-detallado")
@Api(description = "Servicios para consultar reporte de porcentajes de becas")
public class ReportPercentBecaAPI {

    private final ReportPercentBecaService reportPercentBecaService;

    private final UserRequestService userRequestService;

    public ReportPercentBecaAPI(ReportPercentBecaService reportPercentBecaService, UserRequestService userRequestService) {
        this.reportPercentBecaService = reportPercentBecaService;
        this.userRequestService = userRequestService;
    }

    @GetMapping
    @ApiOperation(value = "Obtiene el reporte detallado de actividades")
    public WrapperData getAll(
            @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Ciclo escolar a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "ciclo-escolar", defaultValue = ALL_ITEMS, required = false) String cicloEscolar,
            @ApiParam(value = "Licenciatura a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "licenciatura", defaultValue = ALL_ITEMS, required = false) String licenciatura,
            @ApiParam(value = "Grupo a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "grupo", defaultValue = ALL_ITEMS, required = false) String grupo,
            @ApiParam(value = "Parcial a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "parcial", defaultValue = ALL_ITEMS, required = false) String parcial,
            @ApiParam(value = "Actividad a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "actividad", defaultValue = ALL_ITEMS, required = false) String actividad,
            @ApiParam(value = "Palabra clave") @RequestParam(value = "palabra-clave", defaultValue = "", required = false) String palabraClave,
            HttpServletRequest httpServlet
    ) {

        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        String matricula = ALL_ITEMS;
        if (userLogin.isEsAlumno()) {
            matricula = userLogin.getUsername().trim();
        }

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        Pageable pageable = new Pageable(Integer.parseInt(page), Integer.parseInt(pageSize));


        ReporteActividad reporte = defineFilterParam(cicloEscolar, licenciatura, grupo, parcial, actividad, palabraClave, matricula);

        return this.reportPercentBecaService.getAll(pageable, reporte);
    }

    private ReporteActividad defineFilterParam(String cicloEscolar, String licenciatura, String grupo, String parcial,
                                               String actividad, String palabraClave, String matricula) {
        ReporteActividad filter = new ReporteActividad();

        filter.setCvePeriodo(cicloEscolar);
        filter.setCveLicenciatura(licenciatura);
        filter.setCveGrupo(grupo);
        filter.setMatricula(matricula);


        if (parcial.equalsIgnoreCase(ALL_ITEMS)) {
            filter.setIdParcial(0);
        } else {
            filter.setIdParcial(Integer.parseInt(parcial));
        }

        if (actividad.equalsIgnoreCase(ALL_ITEMS)) {
            filter.setIdActividad(0);
        } else {
            filter.setIdActividad(Integer.parseInt(actividad));
        }

        if (!palabraClave.equalsIgnoreCase("")) {
            filter.setPalabraClave(palabraClave);
        }
        return filter;
    }
}
