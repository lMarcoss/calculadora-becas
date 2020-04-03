package edu.calc.becas.mconfiguracion.parciales.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.mconfiguracion.parciales.dao.QueriesParcial.QRY_GET_ALL_CATALOGUE;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
@Repository
@Slf4j
public class ParcialDaoImpl extends BaseDao implements ParcialDao {
    public ParcialDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public List<Parcial> getAllByPeriodo(String cvePeriodo) {
        return this.jdbcTemplate.query(QueriesParcial.QRY_GET_ALL, new Object[]{cvePeriodo}, ((rs, i) -> mapperParcial(rs)));
    }

    @Override
    public Parcial getParcialActual(CicloEscolarVo cicloEscolarVo) throws GenericException {
        try {
            return this.jdbcTemplate.queryForObject(QueriesParcial.QRY_GET_PARCIAL_ACTUAL,new Object[]{cicloEscolarVo.getClave()}, ((rs, i) -> mapperParcial(rs)));
        }catch (Exception e){
            throw new GenericException( e, "El Parcial actual del periodo en curso no est\u00e1 registrado");
        }

    }

    @Override
    public Parcial getParcialAnterior(Parcial parcialActual) {
        try {
            if(parcialActual.getParcial() > 1){
                return this.jdbcTemplate.queryForObject(QueriesParcial.QRY_GET_PARCIAL_ANTERIOR,
                        new Object[]{parcialActual.getCvePeriodo(), parcialActual.getIdParcial()-1},
                        ((rs, i) -> mapperParcial(rs)));
            }else {
                return null;
            }
        }catch (Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    private Parcial mapperParcial(ResultSet rs) throws SQLException {
        Parcial parcial = new Parcial();
        parcial.setIdParcial(rs.getInt("ID_PARCIAL"));
        parcial.setParcial(rs.getInt("PARCIAL"));
        parcial.setDescParcial(rs.getString("DESC_PARCIAL"));
        parcial.setParcialActual(rs.getString("PARCIAL_ACTUAL"));
        parcial.setFechaInicio(rs.getString("FECHA_INICIO"));
        parcial.setFechaFin(rs.getString("FECHA_FIN"));
        parcial.setCvePeriodo(rs.getString("CVE_PERIODO"));
        parcial.setDescPeriodo(rs.getString("DESC_PERIODO"));
        parcial.setTotalHorasBiblioteca(rs.getInt("TOTAL_HORAS_BIBLIOTECA"));
        parcial.setTotalAsistenciaSala(rs.getInt("TOTAL_ASISTENCIA_SALA"));

        return parcial;
    }

    private Parcial mapperParcialClaveDesc(ResultSet rs) throws SQLException {
        Parcial parcial = new Parcial();
        parcial.setIdParcial(rs.getInt("ID_PARCIAL"));
        parcial.setDescParcial(rs.getString("DESC_PARCIAL"));


        return parcial;
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        List<Parcial> parciales = this.jdbcTemplate.query(QRY_GET_ALL_CATALOGUE, ((rs, i) -> mapperParcialClaveDesc(rs)));
        WrapperData<Parcial> parcialWrapperData = new WrapperData<>();
        parcialWrapperData.setData(parciales);
        parcialWrapperData.setLengthData(parciales.size());
        parcialWrapperData.setPage(0);
        parcialWrapperData.setPageSize(parciales.size());
        return parcialWrapperData;
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return null;
    }

    @Override
    public Parcial add(Parcial p) throws GenericException {
        validateStatus(p);
        try {
            jdbcTemplate.update(QueriesParcial.QRY_ADD,
                    p.getParcial(), p.getParcialActual(), p.getFechaInicio(), p.getFechaFin(),
                    p.getCvePeriodo(), p.getDescPeriodo(), p.getAgregadoPor(), p.getTotalHorasBiblioteca(), p.getTotalAsistenciaSala());
            return p;
        }catch (DataIntegrityViolationException e){
            throw new GenericException("No se puede agregar dos veces el mismo parcial en un periodo");
        }


    }

    private void validateStatus(Parcial p) {
        if (p.getParcialActual().equalsIgnoreCase("S")) {
            jdbcTemplate.update(QueriesParcial.QRY_INACTIVE_ESTATUS);
        }
    }

    @Override
    public Parcial update(Parcial p) {
        validateStatus(p);
        jdbcTemplate.update(QueriesParcial.QRY_UPDATE,
                p.getParcial(), p.getParcialActual(), p.getFechaInicio(), p.getFechaFin(),
                p.getCvePeriodo(), p.getDescPeriodo(), p.getActualizadoPor(), p.getTotalHorasBiblioteca(), p.getTotalAsistenciaSala(), p.getIdParcial());
        return p;
    }
}
