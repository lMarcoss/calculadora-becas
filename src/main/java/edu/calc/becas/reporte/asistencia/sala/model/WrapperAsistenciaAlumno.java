package edu.calc.becas.reporte.asistencia.sala.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WrapperAsistenciaAlumno implements Serializable {
    private List<String> fechas;
    List<AlumnoAsistenciaSala> alumnos;

}
