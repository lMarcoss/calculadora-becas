package edu.calc.becas.mreporte.actividades.percent.beca.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import edu.calc.becas.mreporte.actividades.percent.beca.service.ReportPercentBecaService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 01/05/20
 */
@RestController
@RequestMapping("/reporte-becas")
@Api(tags = "API Reporte Porcentaje de Becas", description = "Servicios para administración de porcentajes de becas")
public class ReportPercentBecaAPI {

    private UserRequestService userRequestService;
    private ReportPercentBecaService reportPercentBecaService;

    public ReportPercentBecaAPI(UserRequestService userRequestService, ReportPercentBecaService reportPercentBecaService) {
        this.userRequestService = userRequestService;
        this.reportPercentBecaService = reportPercentBecaService;
    }

    @PostMapping("/calcula-porcentaje-beca/periodo-en-curso")
    @ApiOperation(value = "Calcula porcentaje de beca de alumnos del periodo en curso")
    public String calculaPorcentajeBecaPorPeriodo(HttpServletRequest httpServletRequest) throws GenericException {
        UserLogin userLogin = userRequestService.getUserLogin(httpServletRequest);
        return reportPercentBecaService.calculaPorcentajeBecaPorPeriodo(userLogin);
    }

    @GetMapping("/detalle/{cve-periodo}")
    @ApiOperation(value = "Obtiene el listado de alumnos con sus respectivos % de beca en el periodo ")
    public WrapperData<ReporteBecaPeriodo> getAll(
            @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Clave Periodo a recuperar", required = true) @PathVariable("cve-periodo") String cvePeriodo) {


        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }

        return reportPercentBecaService.getAllReportByPeriodo(Integer.parseInt(page), Integer.parseInt(pageSize), cvePeriodo);
    }
}
