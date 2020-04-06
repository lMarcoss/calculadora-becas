package edu.calc.becas.malumnos.actividades.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.actividades.model.ActividadAlumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: definicion de servicios para consulta de actividades por alumno(s)
 * Date: 2019-06-16
 */
public interface AlumnoActividadDao {
    ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual);

    WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo);

    List<ActividadAlumno> getActivitiesAlumnos(int idHorario);
}
