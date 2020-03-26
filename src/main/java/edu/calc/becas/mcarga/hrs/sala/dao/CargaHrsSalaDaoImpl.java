package edu.calc.becas.mcarga.hrs.sala.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.CargaHrsDao;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.mreporte.percent.beca.dao.ReportPercentBecaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static edu.calc.becas.mcarga.hrs.sala.dao.QueriesCargaHrsSala.QRY_INSERT_PERCENT_SALA;
import static edu.calc.becas.mcarga.hrs.sala.dao.QueriesCargaHrsSala.QRY_UPDATE_PERCENT_SALA;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 6/3/19
 */
@Repository("cargaHrsSalaRepository")
public class CargaHrsSalaDaoImpl extends BaseDao implements CargaHrsDao {

    private final static Logger LOG = LoggerFactory.getLogger(CargaHrsSalaDaoImpl.class);

    private final ReportPercentBecaDao reportPercentBecaDao;
    private final AlumnoActividadDao alumnoActividadDao;

    public CargaHrsSalaDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty,
                               ReportPercentBecaDao reportPercentBecaDao,
                               AlumnoActividadDao alumnoActividadDao) {
        super(jdbcTemplate, messageApplicationProperty);
        this.reportPercentBecaDao = reportPercentBecaDao;
        this.alumnoActividadDao = alumnoActividadDao;
    }

    @Override
    public int persistenceHours(List<Alumno> alumnos, Parcial parcialActual, CicloEscolarVo cicloEscolarActual) {
        int count = 0;
        for (Alumno alumno : alumnos) {
            try {
                // obtiene la actividad del alumno
                ActividadVo actividadVo = alumnoActividadDao.getActividadByAlumno(alumno.getMatricula(), cicloEscolarActual);

                if (reportPercentBecaDao.actividadAlumnoExists(actividadVo, parcialActual)) {
                    jdbcTemplate.update(QRY_UPDATE_PERCENT_SALA,
                            new Object[]{
                                    alumno.getAsistenciaSala().getPorcentaje(),
                                    actividadVo.getIdActividad(),
                                    parcialActual.getIdParcial()
                            });
                } else {
                    jdbcTemplate.update(QRY_INSERT_PERCENT_SALA,
                            actividadVo.getIdActividad(),
                            alumno.getAsistenciaSala().getPorcentaje(),
                            parcialActual.getParcial(),
                            alumno.getAgregadoPor()
                    );
                }

                count++;
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }

        return count;
    }

}
