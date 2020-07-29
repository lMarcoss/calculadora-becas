package edu.calc.becas.mreporte.actividades.percent.beca.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;

/**
 * Define las operaciones de consulta a la BD para generar reporte de % de becas
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 03/05/20
 */
public interface ReportPercentBecaDao {
    /**
     * Registra % de beca por alumno
     *
     * @param alumnoReporteBecaPeriodo alumno
     * @param userLogin                usuario
     */
    void addPercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin);

    /**
     * Actualiza % de beca por alumno
     *
     * @param alumnoReporteBecaPeriodo % de beca por alumno
     * @param userLogin                usuario
     */
    void updatePercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin);

    WrapperData<AlumnoReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave);
}
