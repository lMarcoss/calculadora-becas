package edu.calc.becas.malumnos.dao;

import edu.calc.becas.common.base.dao.CrudGenericDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

public interface AlumnosDao extends CrudGenericDao<Alumno> {
    WrapperData<Alumno> getAllByStatusLoad(int page, int pageSize, String status, String param1, String param2, String param3);

    Usuario getUserInfo(String matricula);
}
