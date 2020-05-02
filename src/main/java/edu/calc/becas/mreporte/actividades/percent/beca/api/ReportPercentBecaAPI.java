package edu.calc.becas.mreporte.actividades.percent.beca.api;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.percent.beca.service.ReportPercentBecaService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
