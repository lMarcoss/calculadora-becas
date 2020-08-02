package edu.calc.becas.malumnos.actividades.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import org.springframework.stereotype.Service;

/**
 * Servicios para consulta de actividades por alumnos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
@Service
public class AlumnoActividadServiceImpl implements AlumnoActividadService {

    private final AlumnoActividadDao alumnoActividadDao;

    public AlumnoActividadServiceImpl(AlumnoActividadDao alumnoActividadDao) {
        this.alumnoActividadDao = alumnoActividadDao;
    }

    @Override
    public ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual) {
        return alumnoActividadDao.getActividadByAlumno(matricula, cicloEscolarActual);
    }

    @Override
    public WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo, String username) {

        return alumnoActividadDao.getAllAlumnosByActividad(page, pageSize, idActividad, idCiclo, username);
    }


}
