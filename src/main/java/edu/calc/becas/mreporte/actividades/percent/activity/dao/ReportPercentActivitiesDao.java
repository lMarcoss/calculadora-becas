package edu.calc.becas.mreporte.actividades.percent.activity.dao;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;

import java.math.BigDecimal;
import java.util.List;


/**
 * Define las operaciones hacia la BD para generar reporte de porcentaje  de actividades a talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
public interface ReportPercentActivitiesDao {
    /**
     * Valida si la actividad del alumno existe - % de asistencias
     *
     * @param actividadVo   actividad del alumno
     * @param parcialActual parcial  en curso
     * @return actividad existe o no
     */
    boolean actividadAlumnoExists(ActividadVo actividadVo, Parcial parcialActual);

    /**
     * Recuperar los porcentajes de activiades de los alumnos
     *
     * @param pageable              propiedades para recuperar  los registros en forma paginada
     * @param reportPercentActivity dato para filtrar las porcentajes
     * @return reporte
     */
    WrapperData getAll(Pageable pageable, ReportPercentActivity reportPercentActivity);

    /**
     * Registra el porcentaje de actividad del alumno en el taller
     *
     * @param porcentaje        porcentaje
     * @param idActividadAlumno actividad del alumno - taller
     * @param userLogin         usuario
     * @param parcial           parcial del porcentajes
     */
    void addPercentActivity(BigDecimal porcentaje, Integer idActividadAlumno, UserLogin userLogin, Parcial parcial);

    /**
     * Registra el porcentaje de actividad en Sala de computo
     *
     * @param percent           porcentaje
     * @param idActividadAlumno idactividad del alumno - taller aparte de sala
     * @param userLogin         usuario
     * @param parcial           parcial del porcentaje
     */
    void addPercentActivitySala(BigDecimal percent, int idActividadAlumno, UserLogin userLogin, Parcial parcial);

    /**
     * Obtiene los porcentajes de la actividad del alumno en todos los parciales
     *
     * @param actividadAlumno
     * @param periodo
     * @return
     */
    List<ReportPercentActivity> getPercentActivityAllParcial(ActividadVo actividadAlumno, CicloEscolarVo periodo);
}
