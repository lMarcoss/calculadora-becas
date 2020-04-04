package edu.calc.becas.mreporte.asistencia.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mreporte.asistencia.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.model.PaseAsistencia;

import java.util.List;

public interface AsistenciaDao {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(Usuario usuario, String idHorario,
                                                           List<FechaAsistencia> fechasAsistencia,
                                                           Parcial parcialActual,
                                                           Parcial parcialAnterior) throws GenericException;

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario);
}
