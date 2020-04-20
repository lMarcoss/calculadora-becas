package edu.calc.becas.mreporte.actividades.percent.activity.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.ALL_ITEMS;
import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.mreporte.actividades.percent.activity.dao.QueriesReportPercentActivity.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
@Repository
public class ReportPercentActivitiesDaoImpl extends BaseDao implements ReportPercentActivitiesDao {

    public ReportPercentActivitiesDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public boolean actividadAlumnoExists(ActividadVo actividadVo, Parcial parcialActual) {
        try {
            int countAlumno = jdbcTemplate.queryForObject(
                    QRY_COUNT_ID_ACTIVIDAD_ALUMNO,
                    new Object[]{actividadVo.getIdActividad(), parcialActual.getIdParcial(), parcialActual.getCvePeriodo()},
                    Integer.class);
            return countAlumno > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public WrapperData getAll(Pageable pageableQ, ReportPercentActivity filter) {
        boolean pageable = pageableQ.getPageSize() != Integer.parseInt(ITEMS_FOR_PAGE);
        String queryGetALl = QRY_SELECT_DATA_REPORT.concat(QRY_FROM_DATA_REPORTE_ACTIVIDADES);
        String queryCountALl = QRY_COUNT_DATA_REPORT.concat(QRY_FROM_DATA_REPORTE_ACTIVIDADES);

        String conditions = createCondition(filter);
        queryGetALl = queryGetALl.concat(conditions);
        queryCountALl = queryCountALl.concat(conditions);
        queryGetALl = queryGetALl.concat(QRY_ORDER_BY);

        queryGetALl = addQueryPageable(pageableQ.getPage(), pageableQ.getPageSize(), queryGetALl);

        int lengthDataTable = this.jdbcTemplate.queryForObject(queryCountALl, Integer.class);

        List<ReportPercentActivity> data = this.jdbcTemplate.query(queryGetALl, (rs, rowNum) -> mapperReporteActividad(rs));

        int page = pageableQ.getPage();
        int pageSize = pageableQ.getPageSize();

        if (!pageable) {
            page = 0;
            pageSize = lengthDataTable;
        }
        return new WrapperData(data, page, pageSize, lengthDataTable);
    }

    @Override
    public void addPercentActivity(BigDecimal porcentaje, Integer actividad, UserLogin userLogin, Parcial parcial) {
        ActividadVo actividadVo = new ActividadVo();
        actividadVo.setIdActividad(actividad);


        if (actividadAlumnoExists(actividadVo, parcial)) {

            jdbcTemplate.update(QRY_UPDATE_PERCENT_ACTIVIDAD,
                    new Object[]{
                            //percentLibraryTime,
                            porcentaje,
                            userLogin.getUsername(),
                            actividadVo.getIdActividad(),
                            parcial.getIdParcial()

                    });
        } else {
            jdbcTemplate.update(QRY_INSERT_PERCENT_ACTIVIDAD,
                    actividadVo.getIdActividad(),
                    porcentaje,
                    parcial.getIdParcial(),
                    parcial.getCvePeriodo(),
                    parcial.getDescPeriodo(),
                    userLogin.getUsername()
            );
        }
    }

    @Override
    public void addPercentActivitySala(BigDecimal percent, int idActividad, UserLogin userLogin, Parcial parcial) {
        ActividadVo actividadVo = new ActividadVo();
        actividadVo.setIdActividad(idActividad);
        if (actividadAlumnoExists(actividadVo, parcial)) {
            jdbcTemplate.update(QRY_UPDATE_PERCENT_SALA,
                    new Object[]{
                            percent,
                            userLogin.getUsername(),
                            actividadVo.getIdActividad(),
                            parcial.getIdParcial()
                    });
        } else {
            jdbcTemplate.update(QRY_INSERT_PERCENT_SALA,
                    actividadVo.getIdActividad(),
                    percent,
                    parcial.getIdParcial(),
                    parcial.getCvePeriodo(),
                    parcial.getDescPeriodo(),
                    userLogin.getUsername()
            );
        }
    }

    private String createCondition(ReportPercentActivity filter) {
        String conditions = "";
        if (filter.getIdActividad() != 0) {
            conditions = conditions.concat(String.format(ADD_CONDITION_ACTIVIDAD, filter.getIdActividad()));
        }

        if (!filter.getCvePeriodo().equalsIgnoreCase(ALL_ITEMS)) {
            conditions = conditions.concat(String.format(ADD_CONDITION_CICLO_ESCOLAR, "'" + filter.getCvePeriodo() + "'"));
        }

        if (!filter.getCveGrupo().equalsIgnoreCase(ALL_ITEMS)) {
            conditions = conditions.concat(String.format(ADD_CONDITION_GRUPO, "'" + filter.getCveGrupo() + "'"));
        }

        if (!filter.getCveLicenciatura().equalsIgnoreCase(ALL_ITEMS)) {
            conditions = conditions.concat(String.format(ADD_CONDITION_LICENCIATURA, "'" + filter.getCveLicenciatura() + "'"));
        }

        if (filter.getIdParcial() != 0) {
            conditions = conditions.concat(String.format(ADD_CONDITION_PARCIAL, filter.getIdParcial()));
        }

        if (!filter.getMatricula().equalsIgnoreCase(ALL_ITEMS)) {
            conditions = conditions.concat(String.format(ADD_CONDITION_MATRICULA_ALUMNO, filter.getMatricula()));
        }

        if (filter.getPalabraClave() != null && !filter.getPalabraClave().trim().equalsIgnoreCase("")) {
            conditions = conditions.concat(String.format(ADD_CONDITION_LIKE_WORD_KEY, "'%" + filter.getPalabraClave() + "%'"));
        }

        if (filter.getCveEstatus() != null && !filter.getCveEstatus().equalsIgnoreCase(ALL_ITEMS)) {
            conditions = conditions.concat(String.format(ADD_CONDITION_BY_STATUS, "'" + filter.getCveEstatus() + "'"));
        }

        return conditions;
    }

    private ReportPercentActivity mapperReporteActividad(ResultSet rs) throws SQLException {
        ReportPercentActivity reporte = new ReportPercentActivity();
        reporte.setMatricula(rs.getString("MATRICULA"));
        reporte.setNombres(rs.getString("NOMBRES"));
        reporte.setApePaterno(rs.getString("APE_PATERNO"));
        reporte.setApeMaterno(rs.getString("APE_MATERNO"));
        reporte.setPorcentajeSala(rs.getInt("PORCENTAJE_SALA"));
        reporte.setPorcentajeBiblioteca(rs.getInt("PORCENTAJE_BIBLIOTECA"));
        reporte.setPorcentajeActividad(rs.getInt("PORCENTAJE_ACTIVIDAD"));
        reporte.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        reporte.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
        reporte.setIdParcial(rs.getInt("ID_PARCIAL"));
        reporte.setDescParcial(rs.getString("DESC_PARCIAL"));
        reporte.setCvePeriodo(rs.getString("CVE_PERIODO"));
        reporte.setDescPeriodo(rs.getString("DESC_PERIDODO"));
        reporte.setCveGrupo(rs.getString("CVE_GRUPO"));
        reporte.setDescGrupo(rs.getString("DESC_GRUPO"));
        reporte.setCveLicenciatura(rs.getString("CVE_LICENCIATURA"));
        reporte.setDescLicenciatura(rs.getString("DESC_LICENCIATURA"));
        reporte.setCveEstatus(rs.getString("ESTATUS"));
        return reporte;
    }
}
