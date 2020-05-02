package edu.calc.becas.malumnos.model;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.mcarga.hrs.blibioteca.model.Hora;
import edu.calc.becas.mcarga.hrs.sala.model.Asistencia;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 5/13/19
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(description = "Entidad con los datos del alumno")
public class Alumno extends CommonData {
    @ApiModelProperty("Identificador único del alumno")
    private String IdAlumno;

    @ApiModelProperty("CURP Clave Unica de Resgistro de Población")
    private String curp;

    @ApiModelProperty("Matrícula alumno")
    private String matricula;

    @ApiModelProperty("Nombre(s) del alumno")
    private String nombres;

    @ApiModelProperty("Apellido paterno del alumno")
    private String apePaterno;

    @ApiModelProperty("Apellido materno del alumno")
    private String apeMaterno;

    @ApiModelProperty("Horas de biblioteca cumplidas")
    private Hora hora;

    @ApiModelProperty("Asistencias a sala de cómputo")
    private Asistencia asistencia;

    @ApiModelProperty("Actividad en la que está inscripto el alumno")
    private ActividadVo actividad;

    public Alumno(String estatus) {
        super(estatus);
    }

    public LabelValueData horario;

    public String grupo;
    public String dsGrupo;

    public String idDetalleActividad;

    public String cicloEscolar;

    public String licenciatura;
    public String dsLicenciatura;
}
