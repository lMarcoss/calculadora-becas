package edu.calc.becas.malumnos.actividades.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.actividades.model.ActividadAlumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.asistencia.service.AsistenciaService;
import edu.calc.becas.mreporte.percent.beca.service.ReportPercentBecaService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.UtilDate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.ALL_ITEMS;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: servicios para consulta de actividades por alumnos
 * Date: 2019-06-16
 */
@Service
public class AlumnoActividadServiceImpl implements AlumnoActividadService {

    private final AlumnoActividadDao alumnoActividadDao;
    private final CicloEscolarService cicloEscolarService;
    private final ParcialService parcialService;
    private final AsistenciaService asistenciaService;
    private final ReportPercentBecaService reportPercentBecaService;
    private final UsuarioService usuarioService;

    public AlumnoActividadServiceImpl(AlumnoActividadDao alumnoActividadDao, CicloEscolarService cicloEscolarService,
                                      ParcialService parcialService, AsistenciaService asistenciaService,
                                      ReportPercentBecaService reportPercentBecaService, UsuarioService usuarioService) {
        this.alumnoActividadDao = alumnoActividadDao;
        this.cicloEscolarService = cicloEscolarService;
        this.parcialService = parcialService;
        this.asistenciaService = asistenciaService;
        this.reportPercentBecaService = reportPercentBecaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual) {
        return alumnoActividadDao.getActividadByAlumno(matricula, cicloEscolarActual);
    }

    @Override
    public WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo) {

        return alumnoActividadDao.getAllAlumnosByActividad(page, pageSize, idActividad, idCiclo);
    }

    @Override
    public String calculatePercentActivityBySchedule(int idHorario, int parcialOrd, UserLogin userLogin) throws Exception {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        Parcial parcial = parcialService.getParcialByPeriodoAndParcialOrd(parcialOrd, cicloEscolarVo);


        Date dateFechaInicio = UtilDate.convertToDate(parcial.getFechaInicio(), UtilDate.PATTERN_DIAG);
        String fechaInicio = UtilDate.convertDateToString(dateFechaInicio, UtilDate.PATTERN_GUION_INVERSE);

        Date dateFechaFin = UtilDate.convertToDate(parcial.getFechaFin(), UtilDate.PATTERN_DIAG);
        String fechaFin = UtilDate.convertDateToString(dateFechaFin, UtilDate.PATTERN_GUION_INVERSE);

        List<ActividadAlumno> listActivitiesAlumnos = alumnoActividadDao.getActivitiesAlumnos(idHorario);


        if (listActivitiesAlumnos.size() > 0) {
            int totalAsistencias;
            if (listActivitiesAlumnos.get(0).getIdActividad() == 2) {
                totalAsistencias = parcial.getTotalAsistenciaSala();
            } else if (listActivitiesAlumnos.get(0).getIdActividad() == 1) {
                return "Las actividades de biblioteca no pueden calcularse en este servicio";
            } else {
                totalAsistencias = parcial.getTotalAsistenciaActividadExtraEscolar();
            }

            int finalTotalAsistencias = totalAsistencias;
            listActivitiesAlumnos.forEach(actividad -> {
                int asistencias = asistenciaService.countPresenceByActivityAlumno(Integer.valueOf(actividad.getIdAlumno()), fechaInicio, fechaFin);

                BigDecimal asistenciaPorParcial = BigDecimal.valueOf(finalTotalAsistencias);
                BigDecimal aisstenciasAlumno = BigDecimal.valueOf(asistencias);

                BigDecimal multiplica = aisstenciasAlumno.multiply(BigDecimal.valueOf(100));

                BigDecimal percent = multiplica.divide(asistenciaPorParcial, 0, ROUND_HALF_UP);

                if (actividad.getIdActividad() == 2) {
                    reportPercentBecaService.addPercentActivitySala(percent, actividad.getIdActividad(), userLogin, parcial);
                } else {
                    reportPercentBecaService.addPercentActivity(percent, actividad.getIdActividad(), userLogin, parcial);
                }

            });
            return "porcentaje de actividad calculado correctamente";
        } else {
            return "No hay alumnnos en el horario especificado o la actividad no existe";
        }


    }


}
