package edu.calc.becas.malumnos.actividades.dao;

import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: definicion de servicios para consulta de actividades por alumno(s)
 * Date: 2019-06-16
 */
public interface AlumnoActividadDao {
    ActividadVo getActividadByAlumno(String matricula);
}