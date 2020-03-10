package edu.calc.becas.mconfiguracion.cicloescolar.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.mconfiguracion.cicloescolar.dao.QueriesCicloEscolar.QRY_GET_ALL;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 10/03/20
 */
@Repository
public class CicloEscolarDaoImpl extends BaseDao implements CicloEscolarDao {

    public CicloEscolarDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public WrapperData<CicloEscolarVo> getAll() {
        List<CicloEscolarVo> ciclos = this.jdbcTemplate.query(QRY_GET_ALL, ((rs, i) -> mapperCicloEscolar(rs)));
        WrapperData<CicloEscolarVo> ciclo = new WrapperData<>();
        ciclo.setData(ciclos);
        ciclo.setLengthData(ciclos.size());
        ciclo.setPage(0);
        ciclo.setPageSize(ciclos.size());
        return ciclo;
    }

    private CicloEscolarVo mapperCicloEscolar(ResultSet rs) throws SQLException {
        CicloEscolarVo cicloEscolarVo = new CicloEscolarVo();
        cicloEscolarVo.setClave(rs.getString("CVE_PERIODO"));
        cicloEscolarVo.setNombre(rs.getString("DESC_PERIDODO"));
        return cicloEscolarVo;
    }
}
