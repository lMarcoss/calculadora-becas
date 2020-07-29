package edu.calc.becas.mcatalogos.actividades.model;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.malumnos.model.AlumnoActividad;
import edu.calc.becas.mcarga.hrs.blibioteca.model.Hora;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Define las propiedades de un horario de actividad
 */
@Getter
@Setter
@ToString
@ApiModel(value = "DetalleActividad", description = "Entidad con los horarios y detalle de cada acttividad")
public class DetalleActividadVo extends CommonData {
    @ApiModelProperty("Identificador \u00fanico de detalle actividad")
    private int idDetalleActividad;

    @ApiModelProperty("Horario de la actidad")
    private Hora horario;

    @ApiModelProperty("N\u00famero de alumnos inscriptos a la actividad")
    private int numeroAlumnos;

    @ApiModelProperty("Nombre de la actividad")
    private String nombreActividad;

    @ApiModelProperty("Indica el ciclo escolar de la actividad")
    private String idCicloEscolar;

    @ApiModelProperty("Indica el ciclo escolar de la actividad")
    private String cicloEscolar;

    @ApiModelProperty("Identificador \u00fanico de la actividad")
    private int idActividad;

    @ApiModelProperty("Comentario")
    private String comentario;

    @ApiModelProperty("Encargado o responsable de la actividad")
    private Usuario usuario;

    @ApiModelProperty("Periodo al que se encuentra configurada la actividad")
    private CicloEscolarVo periodo;

    @ApiModelProperty("Alumnos relacionados a la actividad")
    private List<AlumnoActividad> alumnos;

    @ApiModelProperty("Parcial actual")
    private Parcial parcial;

    public DetalleActividadVo() {
    }

    public DetalleActividadVo(String estatus) {
        super((estatus));
    }
}
