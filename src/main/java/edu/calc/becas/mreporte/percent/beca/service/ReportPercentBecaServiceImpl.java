package edu.calc.becas.reporte.percent.beca.service;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.reporte.percent.beca.dao.ReportPercentBecaDao;
import edu.calc.becas.reporte.percent.beca.model.ReporteActividad;
import org.springframework.stereotype.Service;

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
}
