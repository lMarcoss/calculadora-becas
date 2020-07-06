package edu.calc.becas.mreporte.actividades.percent.beca.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import edu.calc.becas.mreporte.actividades.percent.beca.service.ReportPercentBecaService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 01/05/20
 */
@RestController
@RequestMapping("/reporte-becas")
@Api(description = "Servicios para administraci\u00f3n de porcentajes de becas")
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

    @GetMapping("/detalle/periodo/{cve-periodo}")
    @ApiOperation(value = "Obtiene la propuesta de beca-colegiatura del periodo cve-periodo")
    public WrapperData<AlumnoReporteBecaPeriodo> getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Palabra clave a buscar")
            @RequestParam(value = "palabra-clave", defaultValue = "", required = false) String palabraClave,

            @ApiParam(value = "Clave Periodo a recuperar", required = true) @PathVariable("cve-periodo") String cvePeriodo) {


        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }

        return reportPercentBecaService.getAllReportByPeriodo(Integer.parseInt(page), Integer.parseInt(pageSize), cvePeriodo, palabraClave);
    }

    @GetMapping("/detalle/periodo/{cve-periodo}/export")
    @ApiOperation(value = "Descargar propuesta de beca-colegiatura del periodo cve-periodo en excel")
    public ResponseEntity<InputStreamResource> exportReportToXLSX(
            @ApiParam(value = "Clave Periodo a recuperar", required = true) @PathVariable("cve-periodo") String cvePeriodo
    ) throws IOException {

        String filename = "PROPUESTA DE BECA COLEGIATURA " + cvePeriodo + ".xlsx";

        InputStreamResource inputStreamResource = reportPercentBecaService.exportDataByPeriodoToXLSX(cvePeriodo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Description", "File Transfer");
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.add("Content-Transfer-Encoding", "binary");
        headers.add("Connection", "Keep-Alive");
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return ResponseEntity.ok().headers(headers).body(inputStreamResource);
    }
}
