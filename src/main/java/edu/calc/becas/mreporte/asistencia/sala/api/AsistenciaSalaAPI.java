package edu.calc.becas.reporte.asistencia.sala.api;

import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.reporte.asistencia.sala.model.AlumnoAsistenciaSala;
import edu.calc.becas.reporte.asistencia.sala.model.FechaAsistencia;
import edu.calc.becas.reporte.asistencia.sala.model.PaseAsistencia;
import edu.calc.becas.reporte.asistencia.sala.model.WrapperAsistenciaAlumno;
import edu.calc.becas.reporte.asistencia.sala.service.AsistenciaSalaService;
import edu.calc.becas.utils.UtilDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.TODAY;

@RestController
@RequestMapping("/asistencia-sala")
@Api(description = "Servicios para consultar asistencia de alumnos en sala de cómputo por usuario")
public class AsistenciaSalaAPI {

    private final AsistenciaSalaService asistenciaSalaService;

    public AsistenciaSalaAPI(AsistenciaSalaService asistenciaSalaService) {
        this.asistenciaSalaService = asistenciaSalaService;
    }

    @PostMapping
    @ApiOperation(value = "Registra una lista de asistencia de alumnos por fecha")
    public List<PaseAsistencia> addPresenceByDate(@RequestBody List<PaseAsistencia> asistencias){
        Usuario usuario  = new Usuario();
        usuario.setUsername("ADMIN");
        return this.asistenciaSalaService.addPresenceByDate(asistencias, usuario);
    }


    @GetMapping("/{username}/{id-horario}")
    @ApiOperation(value = "Obtiene la lista de alumnos por horario")
    public WrapperAsistenciaAlumno getAlumnosByScheduleAndUser(
            @ApiParam(value = "Username", required = true) @PathVariable String username,
            @ApiParam(value = "Identificador-horario", required = true) @PathVariable("id-horario") String idHorario,
            @ApiParam(value = "Fecha inicio", defaultValue = TODAY) @RequestParam(value = "fecha-inicio", defaultValue = TODAY, required = false) String fechaInicio,
            @ApiParam(value = "Fecha Fin", defaultValue = TODAY) @RequestParam(value = "fecha-fin", defaultValue = TODAY, required = false) String fechaFin) throws Exception {
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
        List<AlumnoAsistenciaSala> alumnos = asistenciaSalaService.getAlumnosByScheduleAndUser(username, idHorario, fechas);
        asistenciaAlumno.setAlumnos(alumnos);
        asistenciaAlumno.setFechas(fechas);


        return asistenciaAlumno;
    }

    @GetMapping("/fechas/{fecha-inicio}/{fecha-fin}")
    @ApiOperation(value = "Obtiene el periodo de asistencias entre una fecha de inicio y fin (dd-mm-yyyy)")
    public List<FechaAsistencia> getFechas(@ApiParam(value = "Fecha inicio", required = true) @PathVariable("fecha-inicio") String fechaInicio,
                                           @ApiParam(value = "Fecha fin", required = true) @PathVariable("fecha-fin") String fechaFin) throws Exception {

        Date fechaInicial = UtilDate.convertToDate(fechaInicio);
        Date fechaFinal = UtilDate.convertToDate(fechaFin);

        List<FechaAsistencia> fechas = new ArrayList<>();

        createDate(fechaInicial, fechas);

        while (fechaInicial.before(fechaFinal)) {
            fechaInicial = UtilDate.getNextDayBySum(fechaInicial, 1);
            createDate(fechaInicial, fechas);
        }
        return fechas;
    }

    private void createDate(Date fechaInicial, List<FechaAsistencia> fechas) {
        int day = fechaInicial.getDay();// dias habiles
        if (isDayWeek(day)) {
            FechaAsistencia fechaAsistencia = new FechaAsistencia();
            fechaAsistencia.setDia(String.valueOf(fechaInicial.getDate()));
            fechaAsistencia.setMes(UtilDate.convertMonthToMonthDesc(fechaInicial.getMonth() +1));
            fechaAsistencia.setAnio(String.valueOf(fechaInicial.getYear() + 1900));
            fechaAsistencia.setFechaAsistencia(UtilDate.convertDateToString(fechaInicial, UtilDate.PATTERN_DIAG));
            fechas.add(fechaAsistencia);
        }
    }

    private boolean isDayWeek(int day) {
        return day >= 1 && day <= 5;
    }

}
