package edu.calc.becas.malumnos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.malumnos.model.Alumno;

public interface AlumnosService extends CrudGenericService<Alumno> {
  WrapperData getAllByStatusLoad(int page, int pageSize, String status, String param1);
}
