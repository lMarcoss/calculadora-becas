package edu.calc.becas.malumnos.actividades.model;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Actividades de alumnos", description = "Entidad con lo datos de las actividades con los alumnos")
public class ActividadAlumno extends CommonData {
    private int IdActividadAlumno;
    private String nombreActividad;
    private String idAlumno;
    private String matricula;
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String horario;
    private String grupo;
    private ActividadVo actividad;


    public ActividadAlumno() {

    }

    public ActividadAlumno(String estatus) {
        super(estatus);
    }

}
