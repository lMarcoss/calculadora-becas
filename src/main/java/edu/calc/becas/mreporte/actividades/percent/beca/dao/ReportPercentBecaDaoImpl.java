package edu.calc.becas.mreporte.actividades.percent.beca.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.mreporte.actividades.percent.beca.dao.QueriesReportPercentBeca.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
@Repository
public class ReportPercentBecaDaoImpl extends BaseDao implements ReportPercentBecaDao {
    public ReportPercentBecaDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public void addPercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin) {
        Integer countMatricula = this.jdbcTemplate.queryForObject(
                QRY_VALIDATE_MATRICULA_EXISTS,
                new Object[]{reporteBecaPeriodo.getMatricula(), reporteBecaPeriodo.getCvePeriodo()},
                Integer.class);

        if (countMatricula == null || countMatricula == 0) {
            this.jdbcTemplate.update(QRY_ADD_PERCENT_BECA,
                    reporteBecaPeriodo.getMatricula(),
                    reporteBecaPeriodo.getNombres(),
                    reporteBecaPeriodo.getCveGrupo(),
                    reporteBecaPeriodo.getDescGrupo(),
                    reporteBecaPeriodo.getCveLicenciatura(),
                    reporteBecaPeriodo.getDescLicenciatura(),
                    reporteBecaPeriodo.getCvePeriodo(),
                    reporteBecaPeriodo.getDescPeriodo(),
                    reporteBecaPeriodo.getIdActividad(),
                    reporteBecaPeriodo.getDescActividad(),
                    reporteBecaPeriodo.getPorcentajeBecaTallerCLubP1(),
                    reporteBecaPeriodo.getPorcentajeBecaTallerCLubP2(),
                    reporteBecaPeriodo.getPorcentajeBecaTallerCLubP3(),
                    reporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                    reporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                    reporteBecaPeriodo.getPorcentajeBecaBibliotecaP3(),
                    reporteBecaPeriodo.getPorcentajeBecaSalaComputoP1(),
                    reporteBecaPeriodo.getPorcentajeBecaSalaComputoP2(),
                    reporteBecaPeriodo.getPorcentajeBecaSalaComputoP3(),
                    userLogin.getUsername()

            );
        } else {
            updatePercentBecaByAlumno(reporteBecaPeriodo, userLogin);
        }


    }

    @Override
    public void updatePercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin) {
        this.jdbcTemplate.update(QRY_UPDATE_PERCENT_BECA,
                reporteBecaPeriodo.getNombres(),
                reporteBecaPeriodo.getCveGrupo(),
                reporteBecaPeriodo.getDescGrupo(),
                reporteBecaPeriodo.getCveLicenciatura(),
                reporteBecaPeriodo.getDescLicenciatura(),
                reporteBecaPeriodo.getIdActividad(),
                reporteBecaPeriodo.getDescActividad(),
                reporteBecaPeriodo.getPorcentajeBecaTallerCLubP1(),
                reporteBecaPeriodo.getPorcentajeBecaTallerCLubP2(),
                reporteBecaPeriodo.getPorcentajeBecaTallerCLubP3(),
                reporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                reporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                reporteBecaPeriodo.getPorcentajeBecaBibliotecaP3(),
                reporteBecaPeriodo.getPorcentajeBecaSalaComputoP1(),
                reporteBecaPeriodo.getPorcentajeBecaSalaComputoP2(),
                reporteBecaPeriodo.getPorcentajeBecaSalaComputoP3(),
                userLogin.getUsername(),
                reporteBecaPeriodo.getMatricula(),
                reporteBecaPeriodo.getCvePeriodo()
        );
    }

    @Override
    public WrapperData<ReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo) {
        WrapperData<ReporteBecaPeriodo> reporteBecaPeriodoWrapperData = new WrapperData<>();
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(cvePeriodo);

        if (pageSize != Integer.parseInt(ITEMS_FOR_PAGE)) {
            // paginate rows
            Integer count = this.jdbcTemplate.queryForObject(QRY_COUNT_ALL_BY_PERIODO, new Object[]{cvePeriodo}, Integer.class);
            if (count != null && count > 0) {
                parameters.add(pageSize);
                parameters.add((page * pageSize));

                reporteBecaPeriodoWrapperData.setData(getAllReportByPeriodo(QRY_GET_ALL_REPORTE_BECA.concat(QRY_PAGEABLE), parameters));
                reporteBecaPeriodoWrapperData.setLengthData(count);
            } else {
                reporteBecaPeriodoWrapperData.setData(new ArrayList<>());
            }

        } else {
            // all row for report
            reporteBecaPeriodoWrapperData.setData(
                    getAllReportByPeriodo(QRY_GET_ALL_REPORTE_BECA, parameters)
            );
            if (!reporteBecaPeriodoWrapperData.getData().isEmpty()) {
                reporteBecaPeriodoWrapperData.setLengthData(reporteBecaPeriodoWrapperData.getData().size());
            }

        }


        return reporteBecaPeriodoWrapperData;
    }

    private List<ReporteBecaPeriodo> getAllReportByPeriodo(String qry, ArrayList<Object> parameters) {
        return this.jdbcTemplate.query(qry, parameters.toArray(), (rs, rowNUm) -> mapperReporteBeca(rs));
    }


    private ReporteBecaPeriodo mapperReporteBeca(ResultSet rs) throws SQLException {
        ReporteBecaPeriodo reporteBecaPeriodo = new ReporteBecaPeriodo();
        reporteBecaPeriodo.setMatricula(rs.getString("MATRICULA"));
        reporteBecaPeriodo.setNombres(rs.getString("NOMBRES"));
        reporteBecaPeriodo.setCveGrupo(rs.getString("CVE_GRUPO"));
        reporteBecaPeriodo.setDescGrupo(rs.getString("DESC_GRUPO"));
        reporteBecaPeriodo.setCveLicenciatura(rs.getString("CVE_LICENCIATURA"));
        reporteBecaPeriodo.setDescLicenciatura(rs.getString("DESC_LICENCIATURA"));
        reporteBecaPeriodo.setCvePeriodo(rs.getString("CVE_PERIODO"));
        reporteBecaPeriodo.setDescPeriodo(rs.getString("DESC_PERIDODO"));
        reporteBecaPeriodo.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        reporteBecaPeriodo.setDescActividad(rs.getString("DS_ACTIVIDAD"));
        reporteBecaPeriodo.setPorcentajeBecaTallerCLubP1(rs.getInt("PORCENTAJE_TALLER_CLUB_P1"));
        reporteBecaPeriodo.setPorcentajeBecaTallerCLubP2(rs.getInt("PORCENTAJE_TALLER_CLUB_P2"));
        reporteBecaPeriodo.setPorcentajeBecaTallerCLubP3(rs.getInt("PORCENTAJE_TALLER_CLUB_P3"));
        reporteBecaPeriodo.setPorcentajeBecaBibliotecaP1(rs.getInt("PORCENTAJE_BIBLIOTECA_P1"));
        reporteBecaPeriodo.setPorcentajeBecaBibliotecaP2(rs.getInt("PORCENTAJE_BIBLIOTECA_P2"));
        reporteBecaPeriodo.setPorcentajeBecaBibliotecaP3(rs.getInt("PORCENTAJE_BIBLIOTECA_P3"));
        reporteBecaPeriodo.setPorcentajeBecaSalaComputoP1(rs.getInt("PORCENTAJE_SALA_COMPUTO_P1"));
        reporteBecaPeriodo.setPorcentajeBecaSalaComputoP2(rs.getInt("PORCENTAJE_SALA_COMPUTO_P2"));
        reporteBecaPeriodo.setPorcentajeBecaSalaComputoP3(rs.getInt("PORCENTAJE_SALA_COMPUTO_P3"));
        return reporteBecaPeriodo;
    }
}
