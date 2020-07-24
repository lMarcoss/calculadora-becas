package edu.calc.becas.mreporte.actividades.asistencia.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Define propiedades de una asistencia para calcular porcentajes
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 6/4/19
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(description = "Entidad que define las propiedades de una asistencia - para obtener reportes")
public class Asistencia implements Serializable {
    @ApiModelProperty(value = "Total de  asistencias")
    private int asistencia;
    @ApiModelProperty(value = "Total de faltas")
    private int falta;
    @ApiModelProperty(value = "Porcentaje de asistencias")
    private int porcentaje;
}
