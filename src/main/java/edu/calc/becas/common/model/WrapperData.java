package edu.calc.becas.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Envoltura para regresar registros paginados a la vista del usuario
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/24/19
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Envoltura de informaci\u00f3n paginada de los servicios ${T}")
public class WrapperData<T> implements Serializable {
    @ApiModelProperty("Lista de datos recuperados de un total de lengthData")
    private List<T> data;
    @ApiModelProperty("P\u00e1gina recuperada")
    private int page;
    @ApiModelProperty("Total de p\u00e1ginas")
    private int pageSize;
    @ApiModelProperty("Total de registros")
    private int lengthData;
}
