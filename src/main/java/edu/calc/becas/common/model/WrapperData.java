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
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Wrapper for data send to front
 * Date: 3/24/19
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Envoltura de información paginada de los servicios ${T}")
public class WrapperData<T> implements Serializable {
    @ApiModelProperty("Lista de datos recuperados de un total de lengthData")
    private List<T> data;
    @ApiModelProperty("Página recuperada")
    private int page;
    @ApiModelProperty("Total de páginas")
    private int pageSize;
    @ApiModelProperty("Total de registros")
    private int lengthData;
}
