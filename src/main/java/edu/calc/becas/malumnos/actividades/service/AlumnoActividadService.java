package edu.calc.becas.malumnos.actividades.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
public interface AlumnoActividadService {
    ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual);

    WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo, String username);
}
