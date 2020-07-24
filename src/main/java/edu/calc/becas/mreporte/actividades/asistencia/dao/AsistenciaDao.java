package edu.calc.becas.mreporte.actividades.asistencia.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaTaller;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.List;

/**
 * Define los metodos de consulta para administracion de asistencias
 */
public interface AsistenciaDao {
    /**
     * Obtiene una lista de alumnos de un horario especifico con sus respectivas asistencias
     *
     * @param usuario          usuario que consulta
     * @param idHorario        idHorario a consultar
     * @param fechasAsistencia fecha de asistencia a consultar
     * @param parcialActual    indica el parcial actual del periodo en curso
     * @param parcialAnterior  indica el parcial anterior al actual del periodo en curso
     * @return lista de alumnos con assistencias
     * @throws GenericException
     */
    List<AlumnoAsistenciaTaller> getAlumnosByScheduleAndUser(Usuario usuario, String idHorario,
                                                             List<FechaAsistencia> fechasAsistencia,
                                                             Parcial parcialActual,
                                                             Parcial parcialAnterior) throws GenericException;

    /**
     * Registra o edita una lista de asistencia por fecha
     *
     * @param asistencias     asistencias a registrar
     * @param usuario         usuario o encargado que registra
     * @param parcialActual   parcial en curso
     * @param parcialAnterior parcial anterior del parcial en curso
     * @return lista registrada
     */
    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario, Parcial parcialActual,
                                           Parcial parcialAnterior);

    /**
     * Cuenta las asistencias por  actividad de un alumno
     *
     * @param idActividadAlumno idactividad del alumno
     * @param fechaInicio       fecha inicial del rango a consultar
     * @param fechaFin          fecha final del rango a consultar
     * @return total de asistencias
     */
    int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin);
}
