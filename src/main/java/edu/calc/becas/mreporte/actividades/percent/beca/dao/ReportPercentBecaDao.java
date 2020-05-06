package edu.calc.becas.mreporte.actividades.percent.beca.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
public interface ReportPercentBecaDao {
    void addPercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin);

    void updatePercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin);

    WrapperData<ReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave);
}
