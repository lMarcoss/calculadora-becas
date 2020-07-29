package edu.calc.becas.mcatalogos.defpercentbeca.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.mcatalogos.defpercentbeca.constants.TipoActividad;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mcatalogos.defpercentbeca.model.PorcentajeBecaPorActividad;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.calc.becas.mcatalogos.defpercentbeca.dao.QueriesDefPorcentajeActividad.QRY_GET_ACTIVIDAD_POR_TIPO;

/**
 * Implementa las operaciones de consulta a la BD para definicion de % de becas por taller
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 03/05/20
 */
@Repository
public class DefPorcentajeActividadDaoImpl extends BaseDao implements DefPorcentajeActividadDao {
    public DefPorcentajeActividadDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    /**
     * Recupera las definiciones de % de becas por itpo de taller
     *
     * @return
     */
    @Override
    public DefPorcentajeActividad getDefPorcentajeActividades() {
        DefPorcentajeActividad defPorcentajeActividades = new DefPorcentajeActividad();
        defPorcentajeActividades.setPorcentajeBecaBiblioteca(getDePorcentajeActividad(TipoActividad.BIBLIOTECA));
        defPorcentajeActividades.setPorcentajeBecaSala(getDePorcentajeActividad(TipoActividad.SALA_DE_COMPUTO));
        defPorcentajeActividades.setPorcentajeBecaTaller(getDePorcentajeActividad(TipoActividad.TALLER_O_CLUB));
        return defPorcentajeActividades;
    }

    /**
     * Obtiene la definicion del % por actividad
     *
     * @param tipoActividad actividad
     * @return definicion de %
     */
    private PorcentajeBecaPorActividad getDePorcentajeActividad(String tipoActividad) {
        return this.jdbcTemplate.queryForObject(QRY_GET_ACTIVIDAD_POR_TIPO, new Object[]{tipoActividad}, (rs, i) -> mapperTipoActividad(rs));
    }

    /**
     * Mapeo de definiciones de % de becas
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private PorcentajeBecaPorActividad mapperTipoActividad(ResultSet rs) throws SQLException {
        PorcentajeBecaPorActividad porcentajeBecaPorActividad = new PorcentajeBecaPorActividad();
        porcentajeBecaPorActividad.setCveActividad(rs.getString("CVE_TIPO_ACTIVIDAD"));
        porcentajeBecaPorActividad.setDescTipoActvidad(rs.getString("DS_ACTIVIDAD"));
        porcentajeBecaPorActividad.setPorcentajeMinimoRequerido(rs.getInt("PORCENTAJE_MINIMO_REQUERIDO"));
        porcentajeBecaPorActividad.setPorcentajeBecaActividadIncumplida(rs.getInt("PORCENTAJE_BECA_ACT_INCUMPLIDA"));
        porcentajeBecaPorActividad.setPorcentajeBecaActividadCumplida(rs.getInt("PORCENTAJE_BECA_ACT_CUMPLIDA"));
        return porcentajeBecaPorActividad;
    }
}
