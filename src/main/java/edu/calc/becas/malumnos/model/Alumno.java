package edu.calc.becas.malumnos.model;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.mcarga.hrs.blibioteca.model.Hora;
import edu.calc.becas.mreporte.actividades.asistencia.model.Asistencia;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
    @ApiModelProperty("Identificador \u00fanico del alumno")
    private String IdAlumno;

    @ApiModelProperty("CURP")
    private String curp;

    @ApiModelProperty("Matr\u00edcula alumno")
    private String matricula;

    @ApiModelProperty("Nombre(s) del alumno")
    private String nombres;

    @ApiModelProperty("Apellido paterno del alumno")
    private String apePaterno;

    @ApiModelProperty("Apellido materno del alumno")
    private String apeMaterno;

    @ApiModelProperty("Horas de biblioteca cumplidas")
    private Hora hora;

    @ApiModelProperty("Asistencias a sala de c\u00f3mputo")
    private Asistencia asistencia;

    @ApiModelProperty("Actividad extra-escolar")
    private ActividadVo actividad;

    public Alumno(String estatus) {
        super(estatus);
    }

    private LabelValueData horario;

    private String grupo;
    private String dsGrupo;

    private String idDetalleActividad;

    private String cicloEscolar;

    private String licenciatura;

    private String codigoRFid;
    private String IdLicenciatura;
    private long idAlumnoPeriodo;

}
