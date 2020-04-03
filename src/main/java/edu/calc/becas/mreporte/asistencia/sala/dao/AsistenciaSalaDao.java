package edu.calc.becas.mreporte.asistencia.sala.dao;

import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mreporte.asistencia.sala.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.sala.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.sala.model.PaseAsistencia;

import java.util.List;

public interface AsistenciaSalaDao {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(Usuario usuario, String idHorario,
                                                           List<FechaAsistencia> fechasAsistencia,
                                                           Parcial parcialActual,
                                                           Parcial parcialAnterior);

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario);
}
