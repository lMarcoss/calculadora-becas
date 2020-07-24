package edu.calc.becas.mreporte.actividades.asistencia.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entidad que define las propiedades de una asistencia - customizada para mostrar datos en el front
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Entidad que define las propiedades de una asistencia - customizada para manejo de datos en el front")
public class FechaAsistencia implements Serializable {
    @ApiModelProperty(value = "Fecha")
    private String dia;
    @ApiModelProperty(value = "Mes abreviado")
    private String mes;
    @ApiModelProperty(value = "A\u00f1o")
    private String anio;
    @ApiModelProperty(value = "Identificado de la actividad")
    private int idActividadAlumno;
    @ApiModelProperty(value = "Indica si es asistencia o inasistencia")
    private String asistencia;
    @ApiModelProperty(value = "Fecha de asistencia completa")
    private String fechaAsistencia;
    @ApiModelProperty(value = "Index de la asistencia dentro de la matriz en en front")
    private int index;
    @ApiModelProperty(value = "Es editable o no")
    private boolean edit;
}
