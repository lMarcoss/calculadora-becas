package edu.calc.becas.mconfiguracion.parciales.model;

import edu.calc.becas.common.model.CommonData;
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
 * Date: 4/9/19
 */
@NoArgsConstructor
@Setter
@Getter
@ApiModel(description = "Entidad con los datos del parcial")
public class Parcial extends CommonData implements Serializable {
    @ApiModelProperty("Indentificador único del parcial")
    private int idParcial;

    @ApiModelProperty("Número de Parcial")
    private int parcial;

    @ApiModelProperty("Nombre del parcial")
    private String descParcial;

    @ApiModelProperty("Indica si el parcial es el actual")
    private String parcialActual;

    @ApiModelProperty("Fecha Inicio del parcial")
    private String fechaInicio;

    @ApiModelProperty("Fecha Fin del parcial")
    private String fechaFin;

    @ApiModelProperty("Clave del periodo")
    private String cvePeriodo;

    @ApiModelProperty("Descripción del periodo")
    private String descPeriodo;

    @ApiModelProperty("Total de horas de biblioteca a cumplir durante el parcial")
    private int totalHorasBiblioteca;

    @ApiModelProperty("Total de asistencias de sala de cómputo a cumplir durante el parcial")
    private int totalAsistenciaSala;

    @ApiModelProperty("Total de asistencias a cumplir en actividades extraescolares")
    private int totalAsistenciaActividadExtraEscolar;


    public Parcial(String estatus) {
        super(estatus);
    }

    public void setTotalAsistenciaActividadExtraEscolar(int totalAsistenciaActividadExtraEscolar) {
        this.totalAsistenciaActividadExtraEscolar = totalAsistenciaActividadExtraEscolar;
    }

    public int getTotalAsistenciaActividadExtraEscolar() {
        return totalAsistenciaActividadExtraEscolar;
    }
}
