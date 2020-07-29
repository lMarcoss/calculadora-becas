package edu.calc.becas.mreporte.actividades.percent.activity.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;

import java.math.BigDecimal;
import java.util.List;

/**
 * Define los servicios para calcular porcentaje de asistencias en talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * Date: 2019-07-10
 */
public interface ReportPercentActivitiesService {
    /**
     * recuperar los porcentajes de actividades del los alumnos - % de asistencias
     *
     * @param pageable              informacion para generar resulatdo paginado
     * @param reportPercentActivity filtro para la  consulta
     * @return lista de % de asistencias por filtro
     */
    WrapperData getAll(Pageable pageable, ReportPercentActivity reportPercentActivity);

    /**
     * Registra % de asistencias a un taller o actividdad
     *
     * @param porcentaje        %
     * @param idActividadAlumno actividad del alumno
     * @param userLogin         usuario
     * @param parcial           parcial del % asistencia
     */
    void addPercentActivity(BigDecimal porcentaje, Integer idActividadAlumno, UserLogin userLogin, Parcial parcial);

    /**
     * Registra % de asistencias a sala de computo
     *
     * @param percent           %
     * @param idActividadAlumno id de la actividad del alumno
     * @param userLogin         usuario
     * @param parcial           parcial del % de asistencia
     */
    void addPercentActivitySala(BigDecimal percent, int idActividadAlumno, UserLogin userLogin, Parcial parcial);

    /**
     * Calcula % de asistencias a actividades de un horario en especifico
     *
     * @param idHorario horario de la actividad
     * @param idParcial parcial
     * @param userLogin usuario
     * @return exitoso o no
     * @throws Exception error al cacular % de asistencias
     */
    String calculatePercentActivityBySchedule(int idHorario, int idParcial, UserLogin userLogin) throws Exception;

    /**
     * Calcula el porcentaje de asistencias a talleres de un periodo completo
     *
     * @param userLogin usuario
     * @return exitoso o no
     * @throws GenericException error al generar % de asistencias
     */
    String calculatePercentActivityByPeriodo(UserLogin userLogin) throws GenericException;

    /**
     * Obtiene el % de asistencias de un alumno en un taller en un periodo especifico
     *
     * @param actividadAlumno actividad del alumno
     * @param periodo         periodo a recuperar la informacio
     * @return % de asistencias en el taller especificado
     */
    List<ReportPercentActivity> getPercentActivityAllParcialPeriodo(ActividadVo actividadAlumno, CicloEscolarVo periodo);
}
