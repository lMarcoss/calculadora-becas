package edu.calc.becas.mreporte.actividades.asistencia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WrapperAsistenciaAlumno implements Serializable {
    private List<FechaAsistencia> fechas;
    List<AlumnoAsistenciaTaller> alumnos;

}
