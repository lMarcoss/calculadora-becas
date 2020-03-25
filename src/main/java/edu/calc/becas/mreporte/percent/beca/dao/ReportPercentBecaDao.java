package edu.calc.becas.mreporte.percent.beca.dao;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mreporte.percent.beca.model.ReporteActividad;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
public interface ReportPercentBecaDao {
    boolean actividadAlumnoExists(ActividadVo actividadVo);

    WrapperData getAll(Pageable pageable, ReporteActividad reporteActividad);
}
