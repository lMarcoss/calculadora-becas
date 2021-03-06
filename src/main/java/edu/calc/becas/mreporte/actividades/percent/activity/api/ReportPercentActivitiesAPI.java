package edu.calc.becas.mreporte.actividades.percent.activity.api;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mreporte.actividades.percent.activity.service.ReportPercentActivitiesService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * API para exponer servicios para consultar y calcular los % de asistencias de alumnos a talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
@RestController
@RequestMapping("/reporte-actividades")
@Api(description = "Servicios para administraci\u00f3n de porcentaje de actividades")
public class ReportPercentActivitiesAPI {

    private final ReportPercentActivitiesService reportPercentActivitiesService;

    private final UserRequestService userRequestService;

    public ReportPercentActivitiesAPI(ReportPercentActivitiesService reportPercentActivitiesService, UserRequestService userRequestService) {
        this.reportPercentActivitiesService = reportPercentActivitiesService;
        this.userRequestService = userRequestService;
    }

    /**
     * Servicio que obtiene el reporte detallado de actividades por condiciones y paginacion
     *
     * @param page         pagina a recuperar
     * @param pageSize     registros por pagina
     * @param cicloEscolar periodo para el  filtro
     * @param licenciatura licenciatura para el filtro
     * @param grupo        grupo para el filtro
     * @param parcial      parcial para el filtro
     * @param actividad    actividad para el filtro
     * @param palabraClave palabraClave para el filtro
     * @param estatus      estatus para el filtro
     * @param httpServlet  datos de peticion
     * @return reporte de % de asistencias
     */
    @GetMapping("/detallado")
    @ApiOperation(value = "Obtiene el reporte detallado de actividades")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE) @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Ciclo escolar a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "ciclo-escolar", defaultValue = ALL_ITEMS, required = false) String cicloEscolar,
            @ApiParam(value = "Licenciatura a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "licenciatura", defaultValue = ALL_ITEMS, required = false) String licenciatura,
            @ApiParam(value = "Grupo a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "grupo", defaultValue = ALL_ITEMS, required = false) String grupo,
            @ApiParam(value = "Parcial a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "parcial", defaultValue = ALL_ITEMS, required = false) String parcial,
            @ApiParam(value = "Actividad a recuperar", defaultValue = ALL_ITEMS) @RequestParam(value = "actividad", defaultValue = ALL_ITEMS, required = false) String actividad,
            @ApiParam(value = "Palabra clave") @RequestParam(value = "palabra-clave", defaultValue = "", required = false) String palabraClave,
            @ApiParam(value = "Estatus del alumno en el periodo") @RequestParam(value = "status", defaultValue = ALL_ITEMS, required = false) String estatus,
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


        ReportPercentActivity reporte = defineFilterParam(cicloEscolar, licenciatura, grupo, parcial, actividad, palabraClave, matricula, estatus);

        return this.reportPercentActivitiesService.getAll(pageable, reporte);
    }

    /**
     * Define los parametros de consulta
     *
     * @param cicloEscolar periodo para el  filtro
     * @param licenciatura licenciatura para el  filtro
     * @param grupo        grupo para el  filtro
     * @param parcial      parcial para el  filtro
     * @param actividad    actiidad para el  filtro
     * @param palabraClave palabraclave para el  filtro
     * @param matricula    matricula
     * @param estatus      estatus del regitro
     * @return datos del filtro
     */
    private ReportPercentActivity defineFilterParam(String cicloEscolar, String licenciatura, String grupo, String parcial,
                                                    String actividad, String palabraClave, String matricula, String estatus) {
        ReportPercentActivity filter = new ReportPercentActivity();

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

        filter.setCveEstatus(estatus);
        return filter;
    }


    /**
     * Servicio para calcular % de asistencias a talleres en el periodo actual
     *
     * @param httpServletRequest datos de la peticion
     * @return resultado
     * @throws GenericException error en el calculo
     */
    @PostMapping("/calcula-porcentaje-actividad/periodo-actual")
    @ApiOperation(value = "Calcula porcentaje de actividad extra-escolar de todos los parciales del periodo en curso")
    public String calculatePercentActivityByAllParcialPeriodo(HttpServletRequest httpServletRequest) throws GenericException {
        UserLogin userLogin = userRequestService.getUserLogin(httpServletRequest);
        return reportPercentActivitiesService.calculatePercentActivityByPeriodo(userLogin);
    }

    /**
     * Servicio para calcular los % de asistencias a actividades de un horario y parcial en especifico
     *
     * @param idHorario          horario
     * @param idParcial          parcial
     * @param httpServletRequest datos de la peticion
     * @return resultado
     * @throws Exception error en el calculo
     */
    @PostMapping("/calcula-porcentaje-actividad/horario/{id-horario}/parcial/{parcial}")
    @ApiOperation(value = "Calcula porcentaje de actividad extra-escolar de alumnos por horario de actividad")
    public String calculatePercentActivityBySchedule(
            @ApiParam(value = "Identificador de horario de la actividad extra-escolar", required = true) @PathVariable("id-horario") int idHorario,
            @ApiParam(value = "Parcial del periodo actual a calcular el porcentaje de actividad (1,2 o3)", required = true) @PathVariable("parcial") int idParcial,
            HttpServletRequest httpServletRequest
    ) throws Exception {
        UserLogin userLogin = userRequestService.getUserLogin(httpServletRequest);
        return reportPercentActivitiesService.calculatePercentActivityBySchedule(idHorario, idParcial, userLogin);
    }
}
