package edu.calc.becas.mseguridad.rolesypermisos.model;

import edu.calc.becas.common.model.CommonData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Define las propiedades del Rol de usuario
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "Entidad con los datos del Rol")
public class Rol extends CommonData implements Serializable {
    @ApiModelProperty("Indentificador \u00fanico del Rol")
    private int idRol;
    @ApiModelProperty(value = "Nombre del rol", required = true)
    private String nombre;

    /**
     * Inicializa el rol con estatus
     *
     * @param estatus estatus del Rol
     */
    public Rol(String estatus) {
        super(estatus);
    }
}
