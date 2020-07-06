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
    @ApiModelProperty("Indentificador \u00fanico del parcial")
    private int idParcial;

    @ApiModelProperty("N\u00famero de Parcial (1,2 o 3)")
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

    @ApiModelProperty("Descripci\u00f3n del periodo")
    private String descPeriodo;

    @ApiModelProperty("Total de horas de biblioteca a cumplir durante el parcial")
    private int totalHorasBiblioteca;

    @ApiModelProperty("Total de asistencias de sala de c\u00f3mputo a cumplir durante el parcial")
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
