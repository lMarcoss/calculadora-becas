package edu.calc.becas.mreporte.actividades.percent.activity.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.actividades.model.ActividadAlumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mcatalogos.actividades.service.ActividadesService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.actividades.asistencia.service.AsistenciaService;
import edu.calc.becas.mreporte.actividades.percent.activity.dao.ReportPercentActivitiesDao;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.utils.UtilDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.*;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Implementacion de servicios para calcular % de asistencias a talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * Date: 2019-07-10
 */
@Service
@Slf4j
public class ReportPercentActivitiesServiceImpl implements ReportPercentActivitiesService {

    private final ReportPercentActivitiesDao reportPercentActivitiesDao;
    private final AsistenciaService asistenciaService;
    private final CicloEscolarService cicloEscolarService;
    private final ParcialService parcialService;
    private final AlumnoActividadDao alumnoActividadDao;
    private final ActividadesService actividadesService;

    public ReportPercentActivitiesServiceImpl(ReportPercentActivitiesDao reportPercentActivitiesDao,
                                              AsistenciaService asistenciaService, CicloEscolarService cicloEscolarService,
                                              ParcialService parcialService, AlumnoActividadDao alumnoActividadDao,
                                              ActividadesService actividadesService) {
        this.reportPercentActivitiesDao = reportPercentActivitiesDao;
        this.asistenciaService = asistenciaService;
        this.cicloEscolarService = cicloEscolarService;
        this.parcialService = parcialService;
        this.alumnoActividadDao = alumnoActividadDao;
        this.actividadesService = actividadesService;
    }

    @Override
    public WrapperData getAll(Pageable pageable, ReportPercentActivity reportPercentActivity) {
        return reportPercentActivitiesDao.getAll(pageable, reportPercentActivity);
    }

    @Override
    public void addPercentActivity(BigDecimal porcentaje, Integer idActividadAlumno, UserLogin userLogin, Parcial parcial) {
        reportPercentActivitiesDao.addPercentActivity(porcentaje, idActividadAlumno, userLogin, parcial);
    }

    @Override
    public void addPercentActivitySala(BigDecimal percent, int idActividadAlumno, UserLogin userLogin, Parcial parcial) {
        reportPercentActivitiesDao.addPercentActivitySala(percent, idActividadAlumno, userLogin, parcial);
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
            if (listActivitiesAlumnos.get(0).getActividad().getIdActividad() == 2) {
                // asistencias a sala
                totalAsistencias = parcial.getTotalAsistenciaSala();
            } else if (listActivitiesAlumnos.get(0).getActividad().getIdActividad() == 1) {
                // asistencias a biblioteca
                return "Las actividades de biblioteca no pueden calcularse en este servicio";
            } else {
                // asistencia a actividades extra-curriculares
                totalAsistencias = parcial.getTotalAsistenciaActividadExtraEscolar();
            }

            int finalTotalAsistencias = totalAsistencias;
            listActivitiesAlumnos.forEach(actividad -> {
                int asistencias = asistenciaService.countPresenceByActivityAlumno(actividad.getIdActividadAlumno(), fechaInicio, fechaFin);

                BigDecimal asistenciaPorParcial = BigDecimal.valueOf(finalTotalAsistencias);
                BigDecimal aisstenciasAlumno = BigDecimal.valueOf(asistencias);

                BigDecimal multiplica = aisstenciasAlumno.multiply(BigDecimal.valueOf(100));

                BigDecimal percent = multiplica.divide(asistenciaPorParcial, 0, ROUND_HALF_UP);

                // actividad extraescola del alumno
                ActividadVo actividadExtraEscolar = alumnoActividadDao.getActividadByAlumno(actividad.getMatricula(), cicloEscolarVo);
                actividad.setIdActividadAlumno(actividadExtraEscolar.getIdActividadAlumno());

                if (actividad.getActividad().getIdActividad() == 2) {
                    // sala
                    addPercentActivitySala(percent, actividad.getIdActividadAlumno(), userLogin, parcial);
                } else {
                    // extra-curricular
                    addPercentActivity(percent, actividad.getIdActividadAlumno(), userLogin, parcial);
                }

            });
            return "porcentaje de actividad calculado correctamente";
        } else {
            return "No hay alumnnos en el horario especificado o la actividad no existe";
        }


    }

    @Override
    public String calculatePercentActivityByPeriodo(UserLogin userLogin) throws GenericException {
        CicloEscolarVo periodoActual = cicloEscolarService.getCicloEscolarActual();
        WrapperData<DetalleActividadVo> schedulerActivities = actividadesService.getAllDetalle(0, Integer.parseInt(ITEMS_FOR_PAGE), ALL_ITEMS, periodoActual.getClave(), ESTATUS_ACTIVE, ALL_ITEMS, userLogin);
        List<Parcial> parciales = parcialService.getAllByPeriodo(periodoActual.getClave());

        // para cada horario de actividad - calcula los % de asistencias en cada parcial
        if (schedulerActivities.getData().size() > 0 && parciales.size() > 0) {
            schedulerActivities.getData().forEach(detalleActividadVo -> {
                parciales.forEach(parcial -> {
                    try {
                        String result = calculatePercentActivityBySchedule(detalleActividadVo.getIdDetalleActividad(), parcial.getParcial(), userLogin);
                        log.info(result);
                    } catch (Exception e) {
                        log.error(ExceptionUtils.getStackTrace(e));
                    }
                });
            });
        } else {
            return "No existen actividades/parciales para calcular el porcentaje";
        }

        return "Se ha calculado correctamente el porcentaje de actividades por alumno del periodo en curso";
    }

    @Override
    public List<ReportPercentActivity> getPercentActivityAllParcialPeriodo(ActividadVo actividadAlumno, CicloEscolarVo periodo) {
        return reportPercentActivitiesDao.getPercentActivityAllParcial(actividadAlumno, periodo);
    }
}
