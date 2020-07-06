package edu.calc.becas.common.model;

import edu.calc.becas.common.utils.Constant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra WSur (UNSIS)
 * Description: Common class for properties definition of objects
 * Date: 3/23/19
 */
@Setter
@Getter
@NoArgsConstructor
public class CommonData implements Serializable {
    @ApiModelProperty(value = "Clave del estatus S/N", required = true)
    private String estatus;
    @ApiModelProperty("Descripci\u00f3n el estatus (Activo/Inactivo)")
    private String descEstatus;
    @ApiModelProperty("Nombre de usuario creador")
    private String agregadoPor;
    @ApiModelProperty("Fecha de creaci\u00f3n")
    private String fechaCreacion;
    @ApiModelProperty("Nombre de usuario que edita")
    private String actualizadoPor;
    @ApiModelProperty("Fecha de actualizaci\u00f3n")
    private String fechaActualizacion;

    public CommonData(String estatus) {
        this.estatus = estatus;
        if (this.estatus.equals(Constant.ESTATUS_ACTIVE)) {
            this.descEstatus = "Activo";
        } else if (this.estatus.equals(Constant.ESTATUS_INACTIVE)) {
            this.descEstatus = "Inactivo";
        }
    }
}
