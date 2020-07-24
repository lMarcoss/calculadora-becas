package edu.calc.becas.mreporte.actividades.asistencia.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaTaller;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.List;

/**
 * Define los servicios a exponer para administracion de asistencias a talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 */
public interface AsistenciaService {
    /**
     * Recupera una lista de alumnos con sus respectivas asistencias en caso de que existan
     * Si la asistencia esta registrada se recupera y si no el valor de la asistencia es null
     * Tambien define si la asistencia es editable de acuerdo a la fecha de consulta
     * y fecha de la asistencia y los  dias de tolerancia por usuario para editar las asistencias
     *
     * @param user             usuario o encargado de la actividad
     * @param idHorario        Idhorario de la actividad a obtener las asistencias
     * @param fechasAsistencia lista de fechas en las que se requiere recuperar las asistencias
     * @return asistencias de alumnos en el rango de fechas y horario especificadoo
     * @throws GenericException Error al obtener las asistencias
     */
    List<AlumnoAsistenciaTaller> getAlumnosByScheduleAndUser(UserLogin user, String idHorario, List<FechaAsistencia> fechasAsistencia) throws GenericException;

    /**
     * Registra o edita una lista de asistencias
     *
     * @param asistencias asistencias a registrar o editar
     * @param usuario     usuario o encargado de la actividad
     * @return asistencias registradas o editadas
     * @throws GenericException error al realizar alta o edicion
     */
    List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario) throws GenericException;

    /**
     * Cuenta las asistencias del alumno en una actividad dentro del rango fechaInicio y fechaFin
     *
     * @param idActividadAlumno activida del alumno
     * @param fechaInicio       fecha inicio del rango a recuperar
     * @param fechaFin          fecha fin del rango a recuperar
     * @return total de asistencias del alumno en el rango especificado
     */
    int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin);
}
