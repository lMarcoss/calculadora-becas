package edu.calc.becas.mreporte.actividades.percent.activity.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;

import java.math.BigDecimal;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 2019-07-10
 */
public interface ReportPercentActivitiesService {
    WrapperData getAll(Pageable pageable, ReportPercentActivity reportPercentActivity);

    void addPercentActivity(BigDecimal porcentaje, Integer actividad, UserLogin userLogin, Parcial parcial);

    void addPercentActivitySala(BigDecimal percent, int idActividad, UserLogin userLogin, Parcial parcial);
}
