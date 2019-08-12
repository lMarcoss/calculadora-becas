package edu.calc.becas.mconfiguracion.parciales.model;

import edu.calc.becas.common.model.CommonData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
@NoArgsConstructor
@Setter
@Getter
@ApiModel(description = "Entidad con los datos del parcial")
public class Parcial extends CommonData implements Serializable {
    @ApiModelProperty("Indentificador único del parcial")
    private int idParcial;

    @ApiModelProperty("Nombre del parcial")
    private String descParcial;

    @ApiModelProperty("Indica si el parcial es el actual")
    private boolean parcialActual;

    @ApiModelProperty("Fecha Inicio del parcial")
    private String fechaInicio;

    @ApiModelProperty("Fecha Fin del parcial")
    private String fechaFin;

    @ApiModelProperty("Clave del periodo")
    private String cvePeriodo;

    @ApiModelProperty("Descripción del periodo")
    private String descPeriodo;

}
