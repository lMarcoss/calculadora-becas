package edu.calc.becas.mreporte.actividades.asistencia.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaTaller;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.utils.UtilDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.utils.UtilDate.PATTERN_DIAG;
import static edu.calc.becas.utils.UtilDate.isDateBetween;

/**
 * Implementa las consultas a la BD para administracion de asistencias
 */
@Slf4j
@Repository
public class AsistenciaDaoImpl extends BaseDao implements AsistenciaDao {

    @Value("${prop.carga.hrs.sala.asistencia.asistencia}")
    String pAsistencia;
    @Value("${prop.carga.hrs.sala.asistencia.falta}")
    String pFalta;

    public AsistenciaDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    /**
     * @param usuario          usuario que consulta
     * @param idHorario        idHorario a consultar
     * @param fechasAsistencia fecha de asistencia a consultar
     * @param parcialActual    indica el parcial actual del periodo en curso
     * @param parcialAnterior  indica el parcial anterior al actual del periodo en curso
     * @return
     * @throws GenericException
     */
    @Override
    public List<AlumnoAsistenciaTaller> getAlumnosByScheduleAndUser(Usuario usuario, String idHorario,
                                                                    List<FechaAsistencia> fechasAsistencia,
                                                                    Parcial parcialActual, Parcial parcialAnterior) throws GenericException {

        List<AlumnoAsistenciaTaller> alumnoAsistenciaTallers = jdbcTemplate.query(QueriesAsistenciaSala.GET_ALUMNOS_BY_USER_AND_SCHEDULE,
                new Object[]{idHorario}, ((rs, i) -> mapperAlumnos(rs)));

        Date today = defineDateToday();

        alumnoAsistenciaTallers.forEach(alumnoAsistenciaSala -> {
            List<FechaAsistencia> asistencias = new ArrayList<>();

            fechasAsistencia.forEach(fecha -> {
                FechaAsistencia fechaAsistencia = new FechaAsistencia();


                fechaAsistencia.setAnio(fecha.getAnio());
                fechaAsistencia.setMes(fecha.getMes());
                fechaAsistencia.setDia(fecha.getDia());
                fechaAsistencia.setFechaAsistencia(fecha.getFechaAsistencia());

                boolean editFecha = validateDatePresenceByParcial(parcialActual, fecha.getFechaAsistencia(), parcialAnterior, usuario, today);

                fecha.setEdit(editFecha);


                fechaAsistencia.setIdActividadAlumno(alumnoAsistenciaSala.getIdActividadAlumno());
                try {
                    String asistencia =
                            jdbcTemplate.
                                    queryForObject(QueriesAsistenciaSala.QRY_GET_ASISTENCIA_BY_ACTIVIDAD_ALUMNO,
                                            new Object[]{alumnoAsistenciaSala.getIdActividadAlumno(),
                                                    fecha.getFechaAsistencia()},
                                            String.class);
                    if (asistencia != null) {
                        if (asistencia.equalsIgnoreCase(pAsistencia)) {
                            fechaAsistencia.setAsistencia("true");
                        } else if (asistencia.equalsIgnoreCase(pFalta)) {
                            fechaAsistencia.setAsistencia("false");
                        } else {
                            fechaAsistencia.setAsistencia(null);
                        }
                    }

                } catch (IncorrectResultSizeDataAccessException e) {
                    fechaAsistencia.setAsistencia(null);
                }
                asistencias.add(fechaAsistencia);

            });

            alumnoAsistenciaSala.setAsistencia(asistencias);
        });

        return alumnoAsistenciaTallers;
    }

    /**
     * defina  fecha actual
     *
     * @return fecha actual
     * @throws GenericException error al parsear fecha
     */
    private Date defineDateToday() throws GenericException {
        try {
            return UtilDate.getDateToday();
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException("Error al obtener fecha de hoy " + e.getMessage());
        }
    }

    /**
     * Valida fecha de asistencia por parcial
     *
     * @param parcialActual   parcial en curso
     * @param fecha           fecha de asistencia
     * @param parcialAnterior parcial anterior al parcial en curso
     * @param usuario         usuario o encargado
     * @param today           fecha en curso
     * @return fecha es valida para registrar asistencia
     */
    private boolean validateDatePresenceByParcial(Parcial parcialActual, String fecha,
                                                  Parcial parcialAnterior, Usuario usuario, Date today) {
        try {
            Date fechaAsistencia = UtilDate.convertToDate(fecha, PATTERN_DIAG);
            Date fechaInicio = UtilDate.convertToDate(parcialActual.getFechaInicio(), PATTERN_DIAG);
            Date fechaFin = UtilDate.convertToDate(parcialActual.getFechaFin(), PATTERN_DIAG);

            // fecha de asistencia es del rango de parcial actual sumado la fecha de tolerancia
            if (isDateBetween(fechaInicio, fechaFin, fechaAsistencia) && isDateBetween(fechaInicio, fechaFin, today)) {
                return true;
            }

            // fecha de asistencia es del rango de parcial anterior
            if (usuario.getDiasRetrocesoReporte() > 0 && parcialActual.getParcial() > 1) {
                Date fechaInicioParcialAnterior = UtilDate.convertToDate(parcialAnterior.getFechaInicio(), PATTERN_DIAG);
                Date fechaFinParcialAnterior = UtilDate.convertToDate(parcialAnterior.getFechaFin(), PATTERN_DIAG);
                Date fechaTolerancia = UtilDate.getDateSumDay(fechaFinParcialAnterior, usuario.getDiasRetrocesoReporte());

                // editable si la fecha de asistencia es del parcial anterior
                if (isDateBetween(fechaInicioParcialAnterior, fechaFinParcialAnterior, fechaAsistencia)) {
                    // la fecha actual es del rango de tolerancia del parcial anterior
                    if (isDateBetween(fechaInicioParcialAnterior, fechaTolerancia, today)) {
                        return true;
                    }
                }

            }

        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    @Override
    public List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario, Parcial parcialActual, Parcial parcialAnterior) {
        asistencias.forEach((asistencia) -> {
            try {
                boolean editFecha = validateDatePresenceByParcial(parcialActual, asistencia.getFechaAsistencia(), parcialAnterior, usuario, defineDateToday());

                if (editFecha) {
                    if (asistencia.getAsistencia() != null) {
                        try {
                            this.jdbcTemplate.update(QueriesAsistenciaSala.QRY_ADD_PRESENCE, asistencia.getIdActividadAlumno(),
                                    asistencia.getAsistencia().equalsIgnoreCase("true") ? pAsistencia : pFalta,
                                    asistencia.getFechaAsistencia(), usuario.getUsername());
                            asistencia.setAdded(true);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            try {
                                this.jdbcTemplate.update(QueriesAsistenciaSala.QRY_UPDATE_PRESENCE,
                                        asistencia.getAsistencia().equalsIgnoreCase("true") ? pAsistencia : pFalta,
                                        usuario.getUsername(),
                                        asistencia.getIdActividadAlumno(), asistencia.getFechaAsistencia());
                                asistencia.setUpdated(true);
                            } catch (Exception er) {
                                log.error(er.getMessage());
                            }
                        }
                    } else {
                        asistencia.setMessageError("asistencia null");
                    }
                } else {
                    asistencia.setMessageError("El periodo de reporte para esta asistencia ha finalizado");
                }

            } catch (GenericException e) {
                e.printStackTrace();
            }
        });

        return asistencias;
    }

    @Override
    public int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin) {
        return jdbcTemplate.queryForObject(QueriesAsistenciaSala.QRY_COUNT_ASISTENCIAS, new Object[]{idActividadAlumno, fechaInicio, fechaFin}, Integer.class);
    }

    private FechaAsistencia mapperAsistenciaAlumno(ResultSet rs) {
        return null;
    }

    private AlumnoAsistenciaTaller mapperAlumnos(ResultSet rs) throws SQLException {
        AlumnoAsistenciaTaller alumnoAsistenciaTaller = new AlumnoAsistenciaTaller();
        Alumno alumno = new Alumno();
        alumno.setNombres(rs.getString("NOMBRES"));
        alumno.setApePaterno(rs.getString("APE_PATERNO"));
        alumno.setApeMaterno(rs.getString("APE_MATERNO"));

        alumnoAsistenciaTaller.setAlumno(alumno);

        alumnoAsistenciaTaller.setIdActividadAlumno(rs.getInt("ID_ACTIVIDAD_ALUMNO"));

        alumnoAsistenciaTaller.setIdHorarioActividad(rs.getInt("ID_HORARIO_ACTIVIDAD"));
        //alumnoAsistenciaSala.setAsistencia(true);
        return alumnoAsistenciaTaller;
    }
}
