package edu.calc.becas.mreporte.asistencia.sala.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.mreporte.asistencia.sala.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.sala.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.sala.model.PaseAsistencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.mreporte.asistencia.sala.dao.QueriesAsistenciaSala.*;

@Slf4j
@Repository
public class AsistenciaSalaDaoImpl extends BaseDao implements AsistenciaSalaDao {

    @Value("${prop.carga.hrs.sala.asistencia.asistencia}")
    String pAsistencia;
    @Value("${prop.carga.hrs.sala.asistencia.falta}")
    String pFalta;

    public AsistenciaSalaDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(String username, String idHorario, List<FechaAsistencia> fechasAsistencia) {
        List<AlumnoAsistenciaSala> alumnoAsistenciaSalas = jdbcTemplate.query(GET_ALUMNOS_BY_USER_AND_SCHEDULE, new Object[]{username, idHorario}, ((rs, i) -> mapperAlumnos(rs)));

        alumnoAsistenciaSalas.forEach(alumnoAsistenciaSala -> {
            List<FechaAsistencia> asistencias = new ArrayList<>();

            fechasAsistencia.forEach(fecha -> {
                FechaAsistencia fechaAsistencia = new FechaAsistencia();
                fechaAsistencia.setAnio(fecha.getAnio());
                fechaAsistencia.setMes(fecha.getMes());
                fechaAsistencia.setDia(fecha.getDia());
                fechaAsistencia.setFechaAsistencia(fecha.getFechaAsistencia());

                fechaAsistencia.setIdActividadAlumno(alumnoAsistenciaSala.getIdActividadAlumno());
                try {
                    String asistencia =
                            jdbcTemplate.
                                    queryForObject(QRY_GET_ASISTENCIA_BY_ACTIVIDAD_ALUMNO,
                                            new Object[]{alumnoAsistenciaSala.getIdActividadAlumno(),
                                                    fecha.getFechaAsistencia()},
                                            String.class);
                    if (asistencia != null) {
                        if (asistencia.equalsIgnoreCase(pAsistencia)) {
                            fechaAsistencia.setAsistencia("true");
                        } else if (asistencia.equalsIgnoreCase(pFalta)) {
                            fechaAsistencia.setAsistencia("false");
                        }else {
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
        return alumnoAsistenciaSalas;
    }

    @Override
    public List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario) {

        asistencias.forEach((asistencia)->{
            if(asistencia.getAsistencia() != null){
                try{
                    this.jdbcTemplate.update(QRY_ADD_PRESENCE, asistencia.getIdActividadAlumno(),
                            asistencia.getAsistencia().equalsIgnoreCase("true") ? pAsistencia:pFalta,
                            asistencia.getFechaAsistencia(), usuario.getUsername());
                    asistencia.setAdded(true);
                }catch (Exception e){
                    log.error(e.getMessage());
                    try{
                        this.jdbcTemplate.update(QRY_UPDATE_PRESENCE,
                                asistencia.getAsistencia().equalsIgnoreCase("true") ? pAsistencia:pFalta,
                                usuario.getUsername(),
                                asistencia.getIdActividadAlumno(), asistencia.getFechaAsistencia());
                        asistencia.setUpdated(true);
                    }catch (Exception er){
                        log.error(er.getMessage());
                    }
                }
            }else {
                asistencia.setMessageError("asistencia null");
            }

        });

        return asistencias;
    }

    private FechaAsistencia mapperAsistenciaAlumno(ResultSet rs) {
        return null;
    }

    private AlumnoAsistenciaSala mapperAlumnos(ResultSet rs) throws SQLException {
        AlumnoAsistenciaSala alumnoAsistenciaSala = new AlumnoAsistenciaSala();
        Alumno alumno = new Alumno();
        alumno.setNombres(rs.getString("NOMBRES"));
        alumno.setApePaterno(rs.getString("APE_PATERNO"));
        alumno.setApeMaterno(rs.getString("APE_MATERNO"));

        alumnoAsistenciaSala.setAlumno(alumno);

        alumnoAsistenciaSala.setIdActividadAlumno(rs.getInt("ID_ACTIVIDAD_ALUMNO"));

        alumnoAsistenciaSala.setIdHorarioActividad(rs.getInt("ID_HORARIO_ACTIVIDAD"));
        //alumnoAsistenciaSala.setAsistencia(true);
        return alumnoAsistenciaSala;
    }
}
