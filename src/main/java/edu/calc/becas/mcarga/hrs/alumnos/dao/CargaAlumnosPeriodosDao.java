package edu.calc.becas.mcarga.hrs.alumnos.dao;

import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.alumnos.model.ProcessAlumnos;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;

import java.util.List;

public interface CargaAlumnosPeriodosDao {

  //int persistenceAlumnos(List<Alumno> alumnos, Parcial parcial, CicloEscolarVo cicloEscolarActual);
  ProcessAlumnos persistenceAlumnos(List<Alumno> alumnos, Parcial parcial, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura);

}
