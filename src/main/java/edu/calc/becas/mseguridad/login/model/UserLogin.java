package edu.calc.becas.mseguridad.login.model;

import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
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
@ApiModel(description = "Entidad con los datos del usuario en sesi\u00f3n")
public class UserLogin implements Serializable {

    @ApiModelProperty(value = "D\u00edas de tolerancia para carga de reporte")
    private int diasTolerancia;

    @ApiModelProperty(value = "Nombre del usuario")
    private String nombreUsuario;

    @ApiModelProperty(value = "Apellido paterno del usuario")
    private String apellidoPaterno;
    @ApiModelProperty(value = "Apellido materno del usuario")
    private String apellidoMaterno;

    @ApiModelProperty(value = "correo del usuario", required = true)
    private String username;

    @ApiModelProperty(value = "Contrase\u00f1a del usuario", required = true)
    private String password;

    @ApiModelProperty("Token de sesi\u00f3n")
    private String token;

    @ApiModelProperty("Rol de usuario")
    private String rol;

    @ApiModelProperty("datos de un usuario alumno")
    private Usuario usuarioAlumno;

    @ApiModelProperty("Indica si el usuario en sesi\u00f3n es alumno")
    private boolean esAlumno;

    @ApiModelProperty("Lista de men\u00fas del usuario")
    private List<Menu> menu;

    @ApiModelProperty("Periodo actual")
    private CicloEscolarVo periodoActual;

}
