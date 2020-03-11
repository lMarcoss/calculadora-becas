package edu.calc.becas.mseguridad.login.model;

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
 * Date: 11/03/20
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "Entidad con los datos del usuario para inicio de sesión")
public class UserLogin implements Serializable {
    @ApiModelProperty(value = "correo del usuario", required = true)
    private String username;

    @ApiModelProperty("Contraseña del usuario")
    private String password;

    @ApiModelProperty("Matrícula, si es alumno")
    private String matricula;

    @ApiModelProperty("token de sesión")
    private String token;

    @ApiModelProperty("Rol de usuario")
    private String rol;

}
