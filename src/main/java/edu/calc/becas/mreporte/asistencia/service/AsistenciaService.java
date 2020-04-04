package edu.calc.becas.mreporte.asistencia.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mreporte.asistencia.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.model.PaseAsistencia;

import java.util.List;

public interface AsistenciaService {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(String username, String idHorario, List<FechaAsistencia> fechasAsistencia) throws GenericException;

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario) throws GenericException;
}
