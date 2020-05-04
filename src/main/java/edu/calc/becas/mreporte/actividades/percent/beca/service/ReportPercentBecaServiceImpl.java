package edu.calc.becas.mreporte.actividades.percent.beca.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.actividades.service.AlumnoActividadService;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.malumnos.service.AlumnosService;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mcatalogos.defpercentbeca.service.DefPorcentajeActividadService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.actividades.asistencia.service.AsistenciaService;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mreporte.actividades.percent.activity.service.ReportPercentActivitiesService;
import edu.calc.becas.mreporte.actividades.percent.beca.dao.ReportPercentBecaDao;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
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

    private ReportPercentBecaDao reportPercentBecaDao;
    private CicloEscolarService cicloEscolarService;
    private ParcialService parcialService;
    private AlumnosService alumnosService;
    private AlumnoActividadService alumnoActividadService;
    private ReportPercentActivitiesService reportPercentActivitiesService;
    private AsistenciaService asistenciaService;
    private DefPorcentajeActividadService defPorcentajeActividadService;

    public ReportPercentBecaServiceImpl(ReportPercentBecaDao reportPercentBecaDao, CicloEscolarService cicloEscolarService, ParcialService parcialService,
                                        AlumnosService alumnosService, AlumnoActividadService alumnoActividadService,
                                        ReportPercentActivitiesService reportPercentActivitiesService,
                                        AsistenciaService asistenciaService,
                                        DefPorcentajeActividadService defPorcentajeActividadService) {
        this.reportPercentBecaDao = reportPercentBecaDao;
        this.cicloEscolarService = cicloEscolarService;
        this.parcialService = parcialService;
        this.alumnosService = alumnosService;
        this.alumnoActividadService = alumnoActividadService;
        this.reportPercentActivitiesService = reportPercentActivitiesService;
        this.asistenciaService = asistenciaService;
        this.defPorcentajeActividadService = defPorcentajeActividadService;
    }

    @Override
    public String calculaPorcentajeBecaPorPeriodo(UserLogin userLogin) throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        List<Parcial> parcials = parcialService.getAllByPeriodo(cicloEscolarVo.getClave());
        DefPorcentajeActividad defPorcentajeActividad = defPorcentajeActividadService.getDefPorcentajeActividades();

        List<Alumno> alumnos = alumnosService.getAllByStatusLoad(
                Integer.parseInt(DEFAULT_PAGE),
                Integer.parseInt(ITEMS_FOR_PAGE),
                ALL_ITEMS,
                cicloEscolarVo.getClave(),
                ALL_ITEMS,
                ALL_ITEMS).getData();

        alumnos.forEach(
                alumno -> parcials.forEach(
                        parcial -> calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(
                                alumno,
                                parcial,
                                cicloEscolarVo,
                                defPorcentajeActividad,
                                userLogin
                        )
                )
        );
        return String.format(
                "Se ha calculado correctamente el porcentaje de beca de todos los alumnos del periodo %s",
                cicloEscolarVo.getClave());
    }

    @Override
    public String calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(Alumno alumno, Parcial parcial, CicloEscolarVo periodo,
                                                                   DefPorcentajeActividad defPorcentajeActividad,
                                                                   UserLogin userLogin) {

        ActividadVo actividadAlumno = alumnoActividadService.getActividadByAlumno(alumno.getMatricula(), periodo);
        List<ReportPercentActivity> listPercentByParcial = reportPercentActivitiesService.getPercentActivityAllParcialPeriodo(actividadAlumno, periodo);

        ReporteBecaPeriodo reporteBecaPeriodo = getPercentAllParcial(listPercentByParcial, defPorcentajeActividad);
        // set data by alumno
        reporteBecaPeriodo.setIdActividad(actividadAlumno.getIdActividad());
        reporteBecaPeriodo.setDescActividad(actividadAlumno.getNombreActividad());
        reporteBecaPeriodo.setCveGrupo(alumno.getGrupo());
        reporteBecaPeriodo.setDescGrupo(alumno.getDsGrupo());
        reporteBecaPeriodo.setCveLicenciatura(alumno.getIdLicenciatura());
        reporteBecaPeriodo.setDescLicenciatura(alumno.getLicenciatura());
        reporteBecaPeriodo.setCvePeriodo(periodo.getClave());
        reporteBecaPeriodo.setDescPeriodo(periodo.getNombre());
        reporteBecaPeriodo.setNombres(getNombreAlumno(alumno));
        reporteBecaPeriodo.setMatricula(alumno.getMatricula());

        this.addPercentBecaByAlumno(reporteBecaPeriodo, userLogin);
        return String.format("Se ha calculado correctamnete el porcentaje de beca del alumno %s, parcial %s, periodo %s",
                alumno.getMatricula(), parcial.getParcial(), periodo.getClave());
    }

    @Override
    public void addPercentBecaByAlumno(ReporteBecaPeriodo reporteBecaPeriodo, UserLogin userLogin) {
        reportPercentBecaDao.addPercentBecaByAlumno(reporteBecaPeriodo, userLogin);
    }

    @Override
    public WrapperData<ReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo) {
        return reportPercentBecaDao.getAllReportByPeriodo(page, pageSize, cvePeriodo);
    }

    private String getNombreAlumno(Alumno alumno) {
        return alumno.getNombres() + " " + alumno.getApePaterno() + " " + alumno.getApeMaterno();
    }

    private ReporteBecaPeriodo getPercentAllParcial(List<ReportPercentActivity> listPercentByParcial,
                                                    DefPorcentajeActividad defPorcentajeActividad) {
        ReporteBecaPeriodo reporteBecaPeriodo = new ReporteBecaPeriodo();
        listPercentByParcial.forEach(becaParcial -> {
            if (becaParcial.getParcial() == 1) {

                reporteBecaPeriodo.setPorcentajeBecaBibliotecaP1(getPorcentajeBiblioteca(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaSalaComputoP1(getPorcentajeSalaComputo(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaTallerCLubP1(getPorcentajeTalleClub(defPorcentajeActividad, becaParcial));

            } else if (becaParcial.getParcial() == 2) {
                reporteBecaPeriodo.setPorcentajeBecaBibliotecaP2(getPorcentajeBiblioteca(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaSalaComputoP2(getPorcentajeSalaComputo(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaTallerCLubP2(getPorcentajeTalleClub(defPorcentajeActividad, becaParcial));
            } else if (becaParcial.getParcial() == 3) {
                reporteBecaPeriodo.setPorcentajeBecaBibliotecaP3(getPorcentajeBiblioteca(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaSalaComputoP3(getPorcentajeSalaComputo(defPorcentajeActividad, becaParcial));
                reporteBecaPeriodo.setPorcentajeBecaTallerCLubP3(getPorcentajeTalleClub(defPorcentajeActividad, becaParcial));
            }
            reporteBecaPeriodo.setIdActividad(becaParcial.getIdActividad());
        });
        return reporteBecaPeriodo;
    }

    private int getPorcentajeTalleClub(DefPorcentajeActividad defPorcentajeActividad, ReportPercentActivity becaParcial) {
        return defPorcentajeActividad.getPorcentajeBecaTaller().calculaPorcentajeBecaPorPorcentajeActividad(becaParcial.getPorcentajeActividad());
    }

    private int getPorcentajeSalaComputo(DefPorcentajeActividad defPorcentajeActividad, ReportPercentActivity becaParcial) {
        return defPorcentajeActividad.getPorcentajeBecaSala().calculaPorcentajeBecaPorPorcentajeActividad(becaParcial.getPorcentajeSala());
    }

    private int getPorcentajeBiblioteca(DefPorcentajeActividad defPorcentajeActividad, ReportPercentActivity becaParcial) {
        return defPorcentajeActividad.getPorcentajeBecaBiblioteca().calculaPorcentajeBecaPorPorcentajeActividad(
                becaParcial.getPorcentajeBiblioteca());
    }
}
