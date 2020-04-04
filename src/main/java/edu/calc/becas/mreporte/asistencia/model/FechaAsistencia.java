package edu.calc.becas.mreporte.asistencia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FechaAsistencia implements Serializable {
    private String dia;
    private String mes;
    private String anio;
    private int idActividadAlumno;
    private String asistencia;
    private String fechaAsistencia;
    private int index;
    private boolean edit;
}
