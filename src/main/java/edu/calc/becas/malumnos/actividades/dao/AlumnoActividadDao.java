package edu.calc.becas.malumnos.actividades.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.actividades.model.ActividadAlumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

import java.util.List;

/**
 * Definicion de servicios para consulta de actividades por alumno(s)
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
public interface AlumnoActividadDao {
    /**
     * Obtiene la actividad de un alumno
     *
     * @param matricula          matricula del alumno
     * @param cicloEscolarActual periodo
     * @return actividad del alumno
     */
    ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual);

    /**
     * Obtiene lista de alumnos inscriptos a una actividad
     *
     * @param page        pagina
     * @param pageSize    registros por pagina
     * @param idActividad actividad
     * @param idCiclo     periodo
     * @param username    usuario
     * @return lista de alumnos
     */
    WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo, String username);


    /**
     * Obtiene las claves de actividad_alumno (relacion) de los alumnos en un horario en especifico
     *
     * @param idHorario horario
     * @return lista de actividad_alumno
     */
    List<ActividadAlumno> getActivitiesAlumnos(int idHorario);
}
