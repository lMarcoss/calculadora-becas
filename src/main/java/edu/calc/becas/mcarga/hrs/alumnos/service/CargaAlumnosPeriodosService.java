package edu.calc.becas.mcarga.hrs.alumnos.service;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcarga.hrs.alumnos.model.ProcessAlumnos;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import org.apache.poi.ss.usermodel.Workbook;

public interface CargaAlumnosPeriodosService {
  public ProcessAlumnos processData(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura, String grupo) throws GenericException;
  public int processDataPorcentajes(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) throws GenericException;
}
