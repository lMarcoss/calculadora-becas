package edu.calc.becas.reporte.asistencia.sala.model;

import edu.calc.becas.malumnos.model.Alumno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AlumnoAsistenciaSala implements Serializable {
    private Alumno alumno;
    private int idHorarioActividad;
    private int idActividadAlumno;
}