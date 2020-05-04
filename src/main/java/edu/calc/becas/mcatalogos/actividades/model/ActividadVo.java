package edu.calc.becas.mcatalogos.actividades.model;


import edu.calc.becas.common.model.CommonData;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "Actividad",description = "Entidad con los datos de la actividad")
public class ActividadVo extends CommonData {
    private int idActividad;
    private int idActividadAlumno;
    private String nombreActividad;
    private String cicloEscolar;
    private String obligatorio;
    private int numeroAlumnos;
    private DetalleActividadVo detalle;

    public ActividadVo() {
    }

    public ActividadVo(String estatus) {
        super((estatus));
    }

}
