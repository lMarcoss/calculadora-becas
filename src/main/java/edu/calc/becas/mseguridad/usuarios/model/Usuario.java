package edu.calc.becas.mseguridad.usuarios.model;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * propiedades de un usuario
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "Entidad con los datos del usuario")
public class Usuario extends CommonData implements Serializable {

    @ApiModelProperty("Indentificador \u00fanico del usuario")
    private int idUsuario;
    @ApiModelProperty(value = "Nombre (s) del usuario", required = true)
    private String nombres;
    @ApiModelProperty(value = "Apellido paterno del usuario", required = true)
    private String apePaterno;

    @ApiModelProperty(value = "Apellido materno del usuario")
    private String apeMaterno;

    @ApiModelProperty(value = "Rol del usuario")
    private Rol rol;

    @ApiModelProperty(value = "correo del usuario para sesi\u00f3n", required = true)
    private String username;
    @ApiModelProperty("Contrase\u00f1a del usuario")
    private String password;

    @ApiModelProperty("D\u00edas de tolerania para reporte de asistencias")
    private int diasRetrocesoReporte;
    @ApiModelProperty("Fecha que fue registrada/actualizada D\u00edas de tolerania para reporte de asistencias")
    private LocalDateTime fechaRegistroDiasRetroceso;

    private String commonVal01;
    private String commonVal02;
    private String commonVal03;
    private String commonVal04;
    private String commonVal05;
    private String commonVal06;

    public Usuario(String estatus) {
        super(estatus);
    }
}
