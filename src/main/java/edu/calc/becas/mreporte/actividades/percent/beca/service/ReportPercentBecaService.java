package edu.calc.becas.mreporte.actividades.percent.beca.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Define services to managment beca by periodo
 * Date: 01/05/20
 */
public interface ReportPercentBecaService {
    /**
     * Servicio para calcular % de becas por periodo
     *
     * @param userLogin usuario
     * @return exitoso o no
     * @throws GenericException error en el calculo
     */
    String calculaPorcentajeBecaPorPeriodo(UserLogin userLogin) throws GenericException;

    /**
     * Calcula % de beca del alumno por parcial y periodo
     *
     * @param alumno                 alumno
     * @param parcial                parcial
     * @param periodo                periodo
     * @param defPorcentajeActividad base para calcular %
     * @param userLogin              usuario
     * @return exitoso o no
     */
    String calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(Alumno alumno, Parcial parcial, CicloEscolarVo periodo, DefPorcentajeActividad defPorcentajeActividad, UserLogin userLogin);

    /**
     * Registra % de becas por alumno
     *
     * @param alumnoReporteBecaPeriodo datos de % de becas del alumno
     * @param userLogin                usuario
     */
    void addPercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin);

    /**
     * Recuperar reporte de % de becas por filtro y por paginacion
     *
     * @param page         pagina
     * @param pageSize     registros por pagina
     * @param cvePeriodo   periodo
     * @param palabraClave palabra-clave para el filtro
     * @return reporte
     */
    WrapperData<AlumnoReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave);

    /**
     * Generar el reporte de % de becas por peiodo en excel
     *
     * @param cvePeriodo periodo
     * @return reporte en excel
     * @throws IOException error al generar reporte
     */
    InputStreamResource exportDataByPeriodoToXLSX(String cvePeriodo) throws IOException;
}
