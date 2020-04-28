package edu.calc.becas.malumnos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(description = "Entidad con los datos del alumno asociado a la actividad")
public class AlumnoActividad {

  @ApiModelProperty("Matrícula alumno")
  private String matricula;

  @ApiModelProperty("Grupo alumno")
  private String grupo;

  @ApiModelProperty("Grupo alumno")
  private String licenciatura;


}
