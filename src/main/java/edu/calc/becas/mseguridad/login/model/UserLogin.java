package edu.calc.becas.mseguridad.login.model;

import edu.calc.becas.mseguridad.menu.model.Menu;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty(value = "Nombre del usuario")
    private String nombreUsuario;
    @ApiModelProperty(value = "Apellido paterno del usuario")
    private String apellidoPaterno;
    @ApiModelProperty(value = "Apellido materno del usuario")
    private String apellidoMaterno;

    @ApiModelProperty(value = "correo del usuario", required = true)
    private String username;

    @ApiModelProperty(value = "Contraseña del usuario", required = true)
    private String password;

    @ApiModelProperty("token de sesión")
    private String token;

    @ApiModelProperty("Rol de usuario")
    private String rol;

    @ApiModelProperty("datos de un usuario alumno")
    private Usuario usuarioAlumno;

    @ApiModelProperty("Indica si el usuario en sesión es alumno")
    private boolean esAlumno;

    @ApiModelProperty("Lista de menús del usuario")
    List<Menu> menu;

}
