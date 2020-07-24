package edu.calc.becas.mreporte.actividades.asistencia.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase que representa una asistencia de un alumno en el front - matriz de asistencias
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 04/03/20
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Entidad que define las propiedades una asistencia en el front -  Dato de la casilla")
public class PaseAsistencia implements Serializable {
    @ApiModelProperty(value = "Actividad")
    int idActividadAlumno;
    @ApiModelProperty(value = "Nombre del alumno")
    String alumno;
    @ApiModelProperty(value = "Index de la asistencia dentro de  la matriz de fechas")
    int indexFecha;
    @ApiModelProperty(value = "Fecha de asistencia")
    String fechaAsistencia;
    @ApiModelProperty(value = "Asistencia o inasistencia")
    String asistencia;
    @ApiModelProperty(value = "Asistencia actualizada o no")
    boolean updated;
    @ApiModelProperty(value = "Asistencia agregada o no")
    boolean added;
    @ApiModelProperty(value = "Error ocurrido al editar o guardar asistencia")
    String messageError;
}
