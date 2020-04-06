package edu.calc.becas.mreporte.percent.beca.dao;

import edu.calc.becas.common.model.Pageable;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.percent.beca.model.ReporteActividad;
import edu.calc.becas.mseguridad.login.model.UserLogin;

import java.math.BigDecimal;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
public interface ReportPercentBecaDao {
    boolean actividadAlumnoExists(ActividadVo actividadVo, Parcial parcialActual);

    WrapperData getAll(Pageable pageable, ReporteActividad reporteActividad);

    void addPercentActivity(BigDecimal porcentaje, Integer actividad, UserLogin userLogin, Parcial parcial);

    void addPercentActivitySala(BigDecimal percent, int idActividad, UserLogin userLogin, Parcial parcial);
}
