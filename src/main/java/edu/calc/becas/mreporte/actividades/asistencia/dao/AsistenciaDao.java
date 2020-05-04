package edu.calc.becas.mreporte.actividades.asistencia.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.Date;
import java.util.List;

public interface AsistenciaDao {
    List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(Usuario usuario, String idHorario,
                                                           List<FechaAsistencia> fechasAsistencia,
                                                           Parcial parcialActual,
                                                           Parcial parcialAnterior) throws GenericException;

    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario);

    int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin);
}
