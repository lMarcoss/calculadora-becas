package edu.calc.becas.mreporte.actividades.percent.activity.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.dao.ReportPercentActivitiesDao;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 2019-07-10
 */
@Service
public class ReportPercentActivitiesServiceImpl implements ReportPercentActivitiesService {

    private final ReportPercentActivitiesDao reportPercentActivitiesDao;

    public ReportPercentActivitiesServiceImpl(ReportPercentActivitiesDao reportPercentActivitiesDao) {
        this.reportPercentActivitiesDao = reportPercentActivitiesDao;
    }

    @Override
    public WrapperData getAll(Pageable pageable, ReportPercentActivity reportPercentActivity) {
        return reportPercentActivitiesDao.getAll(pageable, reportPercentActivity);
    }

    @Override
    public void addPercentActivity(BigDecimal porcentaje, Integer actividad, UserLogin userLogin, Parcial parcial) {
        reportPercentActivitiesDao.addPercentActivity(porcentaje, actividad, userLogin, parcial);
    }

    @Override
    public void addPercentActivitySala(BigDecimal percent, int idActividad, UserLogin userLogin, Parcial parcial) {
        reportPercentActivitiesDao.addPercentActivitySala(percent, idActividad, userLogin, parcial);
    }
}
