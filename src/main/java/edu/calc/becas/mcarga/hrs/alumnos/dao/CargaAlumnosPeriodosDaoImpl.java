package edu.calc.becas.mcarga.hrs.alumnos.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static edu.calc.becas.mcarga.hrs.alumnos.dao.QueriesCargaAlumnos.QRY_ADD;
import static edu.calc.becas.mcarga.hrs.alumnos.dao.QueriesCargaAlumnos.QRY_ADD_ALUMNO_PERIODO;


@Repository("cargaAlumnosPeriodosRepository")
@Slf4j
public class CargaAlumnosPeriodosDaoImpl extends BaseDao implements CargaAlumnosPeriodosDao {

    public CargaAlumnosPeriodosDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public int persistenceAlumnos(List<Alumno> alumnos, Parcial parcial, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) {
        int count = 0;
        for (Alumno alumno : alumnos) {
            try {
                this.jdbcTemplate.update(QRY_ADD, createObject(alumno));

            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }

        }


        for (Alumno alumno : alumnos) {
          if(!licenciatura.equals("All")){
            licenciatura = new Licenciatura(alumno.getIdLicenciatura(),alumno.getLicenciatura());
          }
            try {
                this.jdbcTemplate.update(QRY_ADD_ALUMNO_PERIODO, createObjectPeriodo(alumno,
                        cicloEscolarActual,
                        licenciatura));
                count++;
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return count;
    }

    private Object[] createObject(Alumno detalle) {
        return new Object[]{
                detalle.getCurp(),
                detalle.getMatricula(),
                detalle.getNombres(),
                detalle.getApePaterno(),
                detalle.getApeMaterno(),
                "S",
                "ADMIN",
                detalle.getCodigoRFid()
        };
    }

    private Object[] createObjectPeriodo(Alumno detalle, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) {

        return new Object[]{
                detalle.getMatricula(),
                detalle.getGrupo(),
                detalle.getGrupo(),
                licenciatura.getCveLicenciatura(),
                licenciatura.getNombreLicenciatura(),
                cicloEscolarActual.getClave(),
                cicloEscolarActual.getNombre()
        };
    }
}
