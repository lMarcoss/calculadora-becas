package edu.calc.becas.mreporte.actividades.asistencia.model;

import edu.calc.becas.malumnos.model.Alumno;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * Entidad para definir propiedades de un alumno para asistencia en un taller
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 05/04/20
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Entidad con los datos del alumno para registro o edici\u00f3n de asistencia a un taller")
public class AlumnoAsistenciaTaller implements Serializable {
    @ApiModelProperty(value = "Datos del alumno")
    private Alumno alumno;
    @ApiModelProperty(value = "Identificador del horario de la actividad")
    private int idHorarioActividad;

    @ApiModelProperty(value = "Identificador de la actividad del alumno")
    private int idActividadAlumno;

    @ApiModelProperty(value = "Lista de asistencias del alumno por fecha")
    private List<FechaAsistencia> asistencia;
}
