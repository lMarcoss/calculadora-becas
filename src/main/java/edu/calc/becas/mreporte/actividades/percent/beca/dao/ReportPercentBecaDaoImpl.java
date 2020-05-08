package edu.calc.becas.mreporte.actividades.percent.beca.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.apache.commons.lang3.StringUtils;
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
    public void addPercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin) {
        Integer countMatricula = this.jdbcTemplate.queryForObject(
                QRY_VALIDATE_MATRICULA_EXISTS,
                new Object[]{alumnoReporteBecaPeriodo.getMatricula(), alumnoReporteBecaPeriodo.getCvePeriodo()},
                Integer.class);

        if (countMatricula == null || countMatricula == 0) {
            this.jdbcTemplate.update(QRY_ADD_PERCENT_BECA,
                    alumnoReporteBecaPeriodo.getMatricula(),
                    alumnoReporteBecaPeriodo.getNombres(),
                    alumnoReporteBecaPeriodo.getCveGrupo(),
                    alumnoReporteBecaPeriodo.getDescGrupo(),
                    alumnoReporteBecaPeriodo.getCveLicenciatura(),
                    alumnoReporteBecaPeriodo.getDescLicenciatura(),
                    alumnoReporteBecaPeriodo.getCvePeriodo(),
                    alumnoReporteBecaPeriodo.getDescPeriodo(),
                    alumnoReporteBecaPeriodo.getIdActividad(),
                    alumnoReporteBecaPeriodo.getDescActividad(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP3(),
                    alumnoReporteBecaPeriodo.getPorcentajePromedioTallerClub(),
                    alumnoReporteBecaPeriodo.getPorcentajeLogradoTalleClub(),

                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP3(),
                    alumnoReporteBecaPeriodo.getPorcentajePromedioBiblioteca(),
                    alumnoReporteBecaPeriodo.getPorcentajeLogradoBilioteca(),

                    alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP3(),
                    alumnoReporteBecaPeriodo.getPorcentajePromedioSala(),
                    alumnoReporteBecaPeriodo.getPorcentajeLogradoSala(),
                    userLogin.getUsername()

            );
        } else {
            updatePercentBecaByAlumno(alumnoReporteBecaPeriodo, userLogin);
        }


    }

    @Override
    public void updatePercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin) {
        this.jdbcTemplate.update(QRY_UPDATE_PERCENT_BECA,
                alumnoReporteBecaPeriodo.getNombres(),
                alumnoReporteBecaPeriodo.getCveGrupo(),
                alumnoReporteBecaPeriodo.getDescGrupo(),
                alumnoReporteBecaPeriodo.getCveLicenciatura(),
                alumnoReporteBecaPeriodo.getDescLicenciatura(),
                alumnoReporteBecaPeriodo.getIdActividad(),
                alumnoReporteBecaPeriodo.getDescActividad(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP1(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP2(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP3(),
                alumnoReporteBecaPeriodo.getPorcentajePromedioTallerClub(),
                alumnoReporteBecaPeriodo.getPorcentajeLogradoTalleClub(),

                alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP3(),
                alumnoReporteBecaPeriodo.getPorcentajePromedioBiblioteca(),
                alumnoReporteBecaPeriodo.getPorcentajeLogradoBilioteca(),

                alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP1(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP2(),
                alumnoReporteBecaPeriodo.getPorcentajeBecaSalaComputoP3(),
                alumnoReporteBecaPeriodo.getPorcentajePromedioSala(),
                alumnoReporteBecaPeriodo.getPorcentajeLogradoSala(),

                userLogin.getUsername(),
                alumnoReporteBecaPeriodo.getMatricula(),
                alumnoReporteBecaPeriodo.getCvePeriodo()
        );
    }

    @Override
    public WrapperData<AlumnoReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave) {
        WrapperData<AlumnoReporteBecaPeriodo> reporteBecaPeriodoWrapperData = new WrapperData<>();
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(cvePeriodo);

        String qryGetAllRow = QRY_GET_ALL_REPORTE_BECA;

        if(StringUtils.isNotBlank(palabraClave)){
            // add condition search by word key
            qryGetAllRow = addConditionPalabraClave(qryGetAllRow, palabraClave);
        }

        if (pageSize != Integer.parseInt(ITEMS_FOR_PAGE)) {// paginate rows

            String queryCountAll = QRY_COUNT_ALL_BY_PERIODO;

            if(StringUtils.isNotBlank(palabraClave)){
                // add condition search by word key
                queryCountAll = addConditionPalabraClave(queryCountAll, palabraClave);
            }

            Integer count = this.jdbcTemplate.queryForObject(queryCountAll, new Object[]{cvePeriodo}, Integer.class);

            if (count != null && count > 0) {
                parameters.add(pageSize);
                parameters.add((page * pageSize));

                // add query pageable
                reporteBecaPeriodoWrapperData.setData(
                        getAllReportByPeriodo(qryGetAllRow.concat(QRY_PAGEABLE), parameters)
                );

                reporteBecaPeriodoWrapperData.setLengthData(count);
            } else {
                reporteBecaPeriodoWrapperData.setData(new ArrayList<>());
            }

        } else {// all row for report

            reporteBecaPeriodoWrapperData.setData(
                    getAllReportByPeriodo(qryGetAllRow, parameters)
            );
            if (!reporteBecaPeriodoWrapperData.getData().isEmpty()) {
                reporteBecaPeriodoWrapperData.setLengthData(reporteBecaPeriodoWrapperData.getData().size());
            }

        }

        return reporteBecaPeriodoWrapperData;
    }

    private String addConditionPalabraClave(String query, String palabraClave) {
        return query.concat(QRY_ADD_CONDITION_LIKE_PALABRA_CLAVE.replace("?", palabraClave));
    }

    private List<AlumnoReporteBecaPeriodo> getAllReportByPeriodo(String qry, ArrayList<Object> parameters) {
        return this.jdbcTemplate.query(qry, parameters.toArray(), this::mapperReporteBeca);
    }


    private AlumnoReporteBecaPeriodo mapperReporteBeca(ResultSet rs, int rowNum) throws SQLException {
        AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo = new AlumnoReporteBecaPeriodo();
        alumnoReporteBecaPeriodo.setIndex(rowNum);
        alumnoReporteBecaPeriodo.setMatricula(rs.getString("MATRICULA"));
        alumnoReporteBecaPeriodo.setNombres(rs.getString("NOMBRES"));
        alumnoReporteBecaPeriodo.setCveGrupo(rs.getString("CVE_GRUPO"));
        alumnoReporteBecaPeriodo.setDescGrupo(rs.getString("DESC_GRUPO"));
        alumnoReporteBecaPeriodo.setCveLicenciatura(rs.getString("CVE_LICENCIATURA"));
        alumnoReporteBecaPeriodo.setDescLicenciatura(rs.getString("DESC_LICENCIATURA"));
        alumnoReporteBecaPeriodo.setCvePeriodo(rs.getString("CVE_PERIODO"));
        alumnoReporteBecaPeriodo.setDescPeriodo(rs.getString("DESC_PERIDODO"));
        alumnoReporteBecaPeriodo.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        alumnoReporteBecaPeriodo.setDescActividad(rs.getString("DS_ACTIVIDAD"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP1(rs.getInt("PORCENTAJE_TALLER_CLUB_P1"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP2(rs.getInt("PORCENTAJE_TALLER_CLUB_P2"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP3(rs.getInt("PORCENTAJE_TALLER_CLUB_P3"));
        alumnoReporteBecaPeriodo.setPorcentajePromedioTallerClub(rs.getInt("PROMEDIO_PARCIAL_TALLER_CLUB"));
        alumnoReporteBecaPeriodo.setPorcentajeLogradoTalleClub(rs.getInt("PORCENTAJE_LOGRADO_TALLER_CLUB"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP1(rs.getInt("PORCENTAJE_BIBLIOTECA_P1"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP2(rs.getInt("PORCENTAJE_BIBLIOTECA_P2"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP3(rs.getInt("PORCENTAJE_BIBLIOTECA_P3"));
        alumnoReporteBecaPeriodo.setPorcentajePromedioBiblioteca(rs.getInt("PROMEDIO_PARCIAL_BIBLIOTECA"));
        alumnoReporteBecaPeriodo.setPorcentajeLogradoBilioteca(rs.getInt("PORCENTAJE_LOGRADO_BIBLIOTECA"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP1(rs.getInt("PORCENTAJE_SALA_COMPUTO_P1"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP2(rs.getInt("PORCENTAJE_SALA_COMPUTO_P2"));
        alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP3(rs.getInt("PORCENTAJE_SALA_COMPUTO_P3"));
        alumnoReporteBecaPeriodo.setPorcentajePromedioSala(rs.getInt("PROMEDIO_PARCIAL_SALA"));
        alumnoReporteBecaPeriodo.setPorcentajeLogradoSala(rs.getInt("PORCENTAJE_LOGRADO_SALA_COMPUTO"));
        return alumnoReporteBecaPeriodo;
    }
}
