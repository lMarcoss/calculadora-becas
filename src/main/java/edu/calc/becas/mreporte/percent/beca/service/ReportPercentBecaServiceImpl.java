package edu.calc.becas.mreporte.percent.beca.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.percent.beca.dao.ReportPercentBecaDao;
import edu.calc.becas.mreporte.percent.beca.model.ReporteActividad;
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
public class ReportPercentBecaServiceImpl implements ReportPercentBecaService {

    private final ReportPercentBecaDao reportPercentBecaDao;

    public ReportPercentBecaServiceImpl(ReportPercentBecaDao reportPercentBecaDao) {
        this.reportPercentBecaDao = reportPercentBecaDao;
    }

    @Override
    public WrapperData getAll(Pageable pageable, ReporteActividad reporteActividad) {
        return reportPercentBecaDao.getAll(pageable, reporteActividad);
    }

    @Override
    public void addPercentActivity(BigDecimal porcentaje, Integer actividad, UserLogin userLogin, Parcial parcial) {
        reportPercentBecaDao.addPercentActivity(porcentaje, actividad, userLogin, parcial);
    }

    @Override
    public void addPercentActivitySala(BigDecimal percent, int idActividad, UserLogin userLogin, Parcial parcial) {
        reportPercentBecaDao.addPercentActivitySala(percent, idActividad, userLogin, parcial);
    }
}
