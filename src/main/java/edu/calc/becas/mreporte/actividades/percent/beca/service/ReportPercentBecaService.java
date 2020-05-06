package edu.calc.becas.mreporte.actividades.percent.beca.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Define services to managment beca by periodo
 * Date: 01/05/20
 */
public interface ReportPercentBecaService {
    String calculaPorcentajeBecaPorPeriodo(UserLogin userLogin) throws GenericException;

    String calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(Alumno alumno, Parcial parcial, CicloEscolarVo periodo, DefPorcentajeActividad defPorcentajeActividad, UserLogin userLogin);

    void addPercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin);

    WrapperData<ReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave);

    InputStreamResource exportDataByPeriodoToXLSX(String cvePeriodo) throws IOException;
}
