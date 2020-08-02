package edu.calc.becas.malumnos.actividades.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

/**
 * Servicios para consulta de actividades de alumnos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
public interface AlumnoActividadService {
    /**
     * Consulta actividad de un alumno
     *
     * @param matricula          matricula del alumno
     * @param cicloEscolarActual periodo
     * @return actividad del alumno
     */
    ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual);

    /**
     * Consulta los alumnos inscriptos en una actividad
     *
     * @param page        pagina
     * @param pageSize    registros por pagina
     * @param idActividad actividad
     * @param idCiclo     periodo
     * @param username    usuario
     * @return lista de   alumnos
     */
    WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo, String username);
}
