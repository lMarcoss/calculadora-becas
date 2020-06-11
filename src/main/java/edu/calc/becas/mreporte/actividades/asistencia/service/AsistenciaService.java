package edu.calc.becas.mreporte.actividades.asistencia.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.Date;
import java.util.List;

public interface AsistenciaService {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(UserLogin user, String idHorario, List<FechaAsistencia> fechasAsistencia) throws GenericException;

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario) throws GenericException;

    int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin);
}
