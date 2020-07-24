package edu.calc.becas.mreporte.actividades.asistencia.api;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaTaller;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.WrapperAsistenciaAlumno;
import edu.calc.becas.mreporte.actividades.asistencia.service.AsistenciaService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import edu.calc.becas.utils.UtilDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.TODAY;

/**
 * Api para exponer servicios de administracion de asistencia a diferentes talleres incluyendo sala de computo
 */
@RestController
@RequestMapping("/asistencias")
@Api(description = "Servicios para consultar asistencia de alumnos en en diferentes actividades extraescolares")
public class AsistenciaAPI {

    private final AsistenciaService asistenciaService;
    private final UserRequestService userRequestService;

    /**
     * Inicializa los servicios
     *
     * @param asistenciaService  servicio de asistencias a talleres
     * @param userRequestService servicio para interceptar peticiones
     */
    public AsistenciaAPI(AsistenciaService asistenciaService, UserRequestService userRequestService) {
        this.asistenciaService = asistenciaService;
        this.userRequestService = userRequestService;
    }

    /**
     * Registra una lista de asistencias a talleres
     *
     * @param asistencias asistencias a registrar
     * @param httpServlet datos de la peticion
     * @return asistencias
     * @throws GenericException lanza una excepcion con el error ocurrido
     */
    @PostMapping
    @ApiOperation(value = "Registra una lista de asistencia de alumnos por fecha")
    public List<PaseAsistencia> addPresenceByDate(@RequestBody List<PaseAsistencia> asistencias, HttpServletRequest httpServlet) throws GenericException {
        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);
        Usuario usuario = new Usuario();
        usuario.setUsername(userLogin.getUsername());
        return this.asistenciaService.addPresenceByDate(asistencias, usuario);
    }

    /**
     * Obtiene la lista de alumnos por horario para pase de asistencia - valida la fecha actual
     *
     * @param idHorario   horario a recuperar
     * @param fechaInicio fecha de inicio del rango a recuperar
     * @param fechaFin    fecha fin del rango a recuperar
     * @param httpServlet datos de la peticion para obtener usuario
     * @return lista de asistencias con fechas
     * @throws Exception lanza el error ocurrido en el proceso
     */
    @GetMapping("/alumnos-por-fechas-y-horarios/horario/{id-horario}")
    @ApiOperation(value = "Obtiene la lista de alumnos por horario para alta y edici\u00f3n de asistencia")
    public WrapperAsistenciaAlumno getAlumnosByScheduleAndUser(
            @ApiParam(value = "Identificador-horario", required = true) @PathVariable("id-horario") String idHorario,
            @ApiParam(value = "Fecha inicio", defaultValue = TODAY) @RequestParam(value = "fecha-inicio", defaultValue = TODAY, required = false) String fechaInicio,
            @ApiParam(value = "Fecha Fin", defaultValue = TODAY) @RequestParam(value = "fecha-fin", defaultValue = TODAY, required = false) String fechaFin,
            HttpServletRequest httpServlet) throws Exception {

        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        WrapperAsistenciaAlumno asistenciaAlumno = new WrapperAsistenciaAlumno();


        if (fechaInicio.equalsIgnoreCase(TODAY)) {
            Date today = new Date();
            fechaInicio = UtilDate.convertDateToString(today, UtilDate.PATTERN_GUION);
        }

        if (fechaFin.equalsIgnoreCase(TODAY)) {
            Date today = new Date();
            fechaFin = UtilDate.convertDateToString(today, UtilDate.PATTERN_GUION);
        }


        List<FechaAsistencia> fechas = this.getFechas(fechaInicio, fechaFin);
        List<AlumnoAsistenciaTaller> alumnos = asistenciaService.getAlumnosByScheduleAndUser(userLogin, idHorario, fechas);
        asistenciaAlumno.setAlumnos(alumnos);
        asistenciaAlumno.setFechas(fechas);


        return asistenciaAlumno;
    }

    /**
     * Obtiene las fechas en el rango especificado
     *
     * @param fechaInicio inicio del rango
     * @param fechaFin    fin del rango
     * @return fechas en el rango especificado
     * @throws Exception lanza error ocurrido
     */
    private List<FechaAsistencia> getFechas(String fechaInicio,
                                            String fechaFin) throws Exception {

        Date fechaInicial = UtilDate.convertToDate(fechaInicio, null);
        Date fechaFinal = UtilDate.convertToDate(fechaFin, null);

        if (fechaFinal.before(fechaInicial)) {
            throw new Exception("La fecha fin debe ser mayor o igual a la fecha de inicio");
        }

        List<FechaAsistencia> fechas = new ArrayList<>();

        createDate(fechaInicial, fechas);

        while (fechaInicial.before(fechaFinal)) {
            fechaInicial = UtilDate.getDateSumDay(fechaInicial, 1);
            createDate(fechaInicial, fechas);
        }
        return fechas;
    }

    /**
     * crea una lista de fechas dentro del rango fechaInicial y fechaFinal con la condicion de dias habiles
     *
     * @param fechaInicial inicio del rango
     * @param fechas       fechas obtenidas
     */
    private void createDate(Date fechaInicial, List<FechaAsistencia> fechas) {
        int day = fechaInicial.getDay();// dias habiles
        if (isDayWeek(day)) {
            FechaAsistencia fechaAsistencia = new FechaAsistencia();
            fechaAsistencia.setDia(String.valueOf(fechaInicial.getDate()));
            fechaAsistencia.setMes(UtilDate.convertMonthToMonthDesc(fechaInicial.getMonth() + 1));
            fechaAsistencia.setAnio(String.valueOf(fechaInicial.getYear() + 1900));
            fechaAsistencia.setFechaAsistencia(UtilDate.convertDateToString(fechaInicial, UtilDate.PATTERN_DIAG));
            fechas.add(fechaAsistencia);
        }
    }

    /**
     * Valida si la fecha es entre semana
     *
     * @param day dia de la semana
     * @return es dia de la semana o no
     */
    private boolean isDayWeek(int day) {
        return day >= 1 && day <= 5;
    }

}
