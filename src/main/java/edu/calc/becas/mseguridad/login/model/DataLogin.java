package edu.calc.becas.mseguridad.login.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 05/04/20
 */
@Getter
@Setter
@ApiModel(description = "Entidad con los datos del usuario para inicio de sesión")
public class DataLogin implements Serializable {
    @ApiModelProperty(value = "correo del usuario", required = true, position = 0)
    private String username;

    @ApiModelProperty(value = "Contraseña del usuario", required = true,position = 1)
    private String password;
}
