package edu.calc.becas.malumnos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

public interface AlumnosService extends CrudGenericService<Alumno> {
    WrapperData<Alumno> getAllByStatusLoad(int page, int pageSize, String status, String param1, String param2, String param3);

    Usuario getUserInfo(String matricula);
}
