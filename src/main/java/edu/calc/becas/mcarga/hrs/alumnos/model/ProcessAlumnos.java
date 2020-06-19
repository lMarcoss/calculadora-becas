package edu.calc.becas.mcarga.hrs.alumnos.model;

import edu.calc.becas.malumnos.model.Alumno;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProcessAlumnos {
  private int processSuccess;
  private int processError;
  private List<Alumno> alumno;
}
