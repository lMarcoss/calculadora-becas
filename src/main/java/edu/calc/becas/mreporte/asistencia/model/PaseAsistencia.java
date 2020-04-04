package edu.calc.becas.mreporte.asistencia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Clase que representa una asistencia de un alumno
 * Date: 04/03/20
 */
@Getter
@Setter
@NoArgsConstructor
public class PaseAsistencia implements Serializable {
    int idActividadAlumno;
    String alumno;
    int indexFecha;
    String fechaAsistencia;
    String asistencia;
    boolean updated;
    boolean added;
    String messageError;
}
