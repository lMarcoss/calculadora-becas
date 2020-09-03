package edu.calc.becas.mcarga.hrs.blibioteca.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.CargaHrsDao;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.dao.ReportPercentActivitiesDao;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static edu.calc.becas.mcarga.hrs.blibioteca.dao.QueriesCargaHrsBiblioteca.QRY_INSERT_PERCENT_BIBLIOTECA;
import static edu.calc.becas.mcarga.hrs.blibioteca.dao.QueriesCargaHrsBiblioteca.QRY_UPDATE_PERCENT_BIBLIOTECA;

/**
 * Operaciones para guardar horas de biblioteca de los alumnos en la BD
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 6/3/19
 */
@Slf4j
@Repository("cargaHrsBibliotecaRepository")
public class CargaHrsBibliotecaDaoImpl extends BaseDao implements CargaHrsDao {


    private final ReportPercentActivitiesDao reportPercentActivitiesDao;
    private final AlumnoActividadDao alumnoActividadDao;

    public CargaHrsBibliotecaDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty,
                                     ReportPercentActivitiesDao reportPercentActivitiesDao,
                                     AlumnoActividadDao alumnoActividadDao) {
        super(jdbcTemplate, messageApplicationProperty);
        this.reportPercentActivitiesDao = reportPercentActivitiesDao;
        this.alumnoActividadDao = alumnoActividadDao;
    }

    /**
     * Registra las horas de biblioteca de los alumnos en un parcial
     *
     * @param alumnos            lista de alumnnos con sus horas
     * @param parcial            parcial
     * @param cicloEscolarActual periodo
     * @return total  de registros guardados y actualizados
     */
    @Override
    public int persistenceHours(List<Alumno> alumnos, Parcial parcial, CicloEscolarVo cicloEscolarActual) {
        int count = 0;
        for (Alumno alumno : alumnos) {
            try {
                // obtiene datos del alumno
                /*Alumno alumnoBD = alumnosService.getByMatricula(alumno.getMatricula());*/
                int percentLibraryTime = calculatePercentHoursLibrary(alumno, parcial);
                // obtiene la actividad del alumno
                ActividadVo actividadVo = alumnoActividadDao.getActividadByAlumno(alumno.getMatricula(), cicloEscolarActual);
                int updated = 0;
                if (reportPercentActivitiesDao.actividadAlumnoExists(actividadVo, parcial)) {
                    updated = jdbcTemplate.update(QRY_UPDATE_PERCENT_BIBLIOTECA,
                            new Object[]{
                                    percentLibraryTime,
                                    actividadVo.getIdActividadAlumno(),
                                    parcial.getIdParcial()
                            });
                } else {
                    updated = jdbcTemplate.update(QRY_INSERT_PERCENT_BIBLIOTECA,
                            actividadVo.getIdActividadAlumno(),
                            percentLibraryTime,
                            parcial.getIdParcial(),
                            cicloEscolarActual.getClave(),
                            cicloEscolarActual.getNombre(),
                            alumno.getAgregadoPor()
                    );
                }
                if (updated != 0) {
                    count++;
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
        return count;
    }

    /**
     * Clcula el % de horas de un alumno en un parcial
     *
     * @param alumno
     * @param parcialActual
     * @return
     */
    private int calculatePercentHoursLibrary(Alumno alumno, Parcial parcialActual) {
        int horasParcial = parcialActual.getTotalHorasBiblioteca() * 60;

        int horasALumno = 0;
        if (alumno.getHora() != null) {
            horasALumno = (alumno.getHora().getNumHora() * 60) + alumno.getHora().getNumMinutos();
        }
        return (horasALumno * 100) / horasParcial;
    }

}
