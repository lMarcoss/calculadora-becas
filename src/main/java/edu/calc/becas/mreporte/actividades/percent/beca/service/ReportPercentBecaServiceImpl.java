package edu.calc.becas.mreporte.actividades.percent.beca.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.actividades.service.AlumnoActividadService;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.malumnos.service.AlumnosService;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.actividades.asistencia.service.AsistenciaService;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mreporte.actividades.percent.activity.service.ReportPercentActivitiesService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 01/05/20
 */
@Service
public class ReportPercentBecaServiceImpl implements ReportPercentBecaService {

    private CicloEscolarService cicloEscolarService;
    private ParcialService parcialService;
    private AlumnosService alumnosService;
    private AlumnoActividadService alumnoActividadService;
    private ReportPercentActivitiesService reportPercentActivitiesService;
    private AsistenciaService asistenciaService;

    public ReportPercentBecaServiceImpl(CicloEscolarService cicloEscolarService, ParcialService parcialService,
                                        AlumnosService alumnosService, AlumnoActividadService alumnoActividadService,
                                        ReportPercentActivitiesService reportPercentActivitiesService,
                                        AsistenciaService asistenciaService) {
        this.cicloEscolarService = cicloEscolarService;
        this.parcialService = parcialService;
        this.alumnosService = alumnosService;
        this.alumnoActividadService = alumnoActividadService;
        this.reportPercentActivitiesService = reportPercentActivitiesService;
        this.asistenciaService = asistenciaService;
    }

    @Override
    public String calculaPorcentajeBecaPorPeriodo(UserLogin userLogin) throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        List<Parcial> parcials = parcialService.getAllByPeriodo(cicloEscolarVo.getClave());

        List<Alumno> alumnos = alumnosService.getAllByStatusLoad(
                Integer.parseInt(DEFAULT_PAGE),
                Integer.parseInt(ITEMS_FOR_PAGE),
                ALL_ITEMS,
                ALL_ITEMS,
                ALL_ITEMS,
                ALL_ITEMS).getData();

        alumnos.forEach(
                alumno -> parcials.forEach(
                        parcial -> calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(
                                alumno,
                                parcial,
                                cicloEscolarVo
                        )
                )
        );
        return String.format(
                "Se ha calculado correctamente el porcentaje de beca de todos los alumnos del periodo %s",
                cicloEscolarVo.getClave());
    }

    @Override
    public String calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(Alumno alumno, Parcial parcial, CicloEscolarVo periodo) {
        ActividadVo actividadAlumno = alumnoActividadService.getActividadByAlumno(alumno.getMatricula(), periodo);
        List<ReportPercentActivity> listPercentByParcialAnPeriodo = reportPercentActivitiesService.getPercentActivityAllParcialPeriodo(actividadAlumno, periodo);
        return String.format("Se ha calculado correctamnete el porcentaje de beca del alumno %s, parcial %s, periodo %s",
                alumno.getMatricula(), parcial.getParcial(), periodo.getClave());
    }
}
