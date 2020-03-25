package edu.calc.becas.mreporte.asistencia.sala.service;

import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mreporte.asistencia.sala.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.sala.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.sala.model.PaseAsistencia;

import java.util.List;

public interface AsistenciaSalaService {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(String username, String idHorario, List<FechaAsistencia> fechasAsistencia);

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario);
}
