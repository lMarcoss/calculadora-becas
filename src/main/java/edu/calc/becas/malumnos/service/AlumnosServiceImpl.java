package edu.calc.becas.malumnos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.dao.AlumnosDao;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vampiro
 */
@Service
public class AlumnosServiceImpl implements AlumnosService {

    private final AlumnosDao alumnosDao;

    @Autowired
    public AlumnosServiceImpl(AlumnosDao alumnosDao) {
        this.alumnosDao = alumnosDao;
    }


    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return null;
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String idActividad) {
        return alumnosDao.getAllByStatusAndOneParam(page, pageSize, status, idActividad);
    }

    @Override
    public Alumno add(Alumno alumno) throws GenericException {
        return alumnosDao.add(alumno);
    }

    @Override
    public Alumno update(Alumno alumno) {
        return null;
    }

    @Override
    public WrapperData<Alumno> getAllByStatusLoad(int page, int pageSize, String status, String periodo, String licenciatura, String grupo) {
        return alumnosDao.getAllByStatusLoad(page, pageSize, status, periodo, licenciatura, grupo);
    }

    @Override
    public Usuario getUserInfo(String matricula) {
        return alumnosDao.getUserInfo(matricula);
    }
}
