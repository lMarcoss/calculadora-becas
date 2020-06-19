package edu.calc.becas.mcatalogos.licenciaturas.model;

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
 * Date: 3/23/19
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Entidad con los datos de la licenciatura en el periodo actual")
public class Licenciatura implements Serializable {

    @ApiModelProperty(notes = "Clave de la licenciatura", required = true)
    private String cveLicenciatura;
    @ApiModelProperty(notes = "Nombre de la licenciatura", required = true)
    private String nombreLicenciatura;
    @ApiModelProperty(notes = "Indica si es vigente la licenciatura", required = true)
    private boolean vigente;

  public Licenciatura(String cveLicenciatura, String nombreLicenciatura) {
    this.cveLicenciatura = cveLicenciatura;
    this.nombreLicenciatura = nombreLicenciatura;
  }
}
