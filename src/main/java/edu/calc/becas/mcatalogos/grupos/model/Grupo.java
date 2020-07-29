package edu.calc.becas.mcatalogos.grupos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Define las propiedades de un grupo
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Entidad que representa los datos de un grupo en el perido actual")
public class Grupo {

    @ApiModelProperty(value = "Clave del grupo", required = true)
    private String cveGrupo;

    @ApiModelProperty(value = "Nombre del grupo", required = true)
    private String nombreGrupo;

    @ApiModelProperty(value = "Clave de la Licenciatura", required = true)
    private String cveLicenciatura;

    @ApiModelProperty(value = "Nombre de la Licenciatura", required = true)
    private String nombreLicenciatura;

    @ApiModelProperty(value = "Clave periodo", required = true)
    private String cvePeriodo;

    @ApiModelProperty(value = "Nombre Periodo", required = true)
    private String nombrePeriodo;
}
