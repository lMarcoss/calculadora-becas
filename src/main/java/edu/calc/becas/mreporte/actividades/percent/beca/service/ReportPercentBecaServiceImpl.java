package edu.calc.becas.mreporte.actividades.percent.beca.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.excel.write.report.beca.periodo.ExcelWritterReportBeca;
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
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 01/05/20
 */
@Service
@Slf4j
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
                        parcial -> {
                            try{
                                calculaPorcentajeBecaDelAlumnoPorParcialYPeriodo(
                                        alumno,
                                        parcial,
                                        cicloEscolarVo,
                                        defPorcentajeActividad,
                                        userLogin
                                );
                            }catch (Exception e){
                                log.error(e.getMessage());
                            }
                        }
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

        AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo = getPercentAllParcial(listPercentByParcial, defPorcentajeActividad);
        // set data by alumno
        alumnoReporteBecaPeriodo.setIdActividad(actividadAlumno.getIdActividad());
        alumnoReporteBecaPeriodo.setDescActividad(actividadAlumno.getNombreActividad());
        alumnoReporteBecaPeriodo.setCveGrupo(alumno.getGrupo());
        alumnoReporteBecaPeriodo.setDescGrupo(alumno.getDsGrupo());
        alumnoReporteBecaPeriodo.setCveLicenciatura(alumno.getIdLicenciatura());
        alumnoReporteBecaPeriodo.setDescLicenciatura(alumno.getLicenciatura());
        alumnoReporteBecaPeriodo.setCvePeriodo(periodo.getClave());
        alumnoReporteBecaPeriodo.setDescPeriodo(periodo.getNombre());
        alumnoReporteBecaPeriodo.setNombres(getNombreAlumno(alumno));
        alumnoReporteBecaPeriodo.setMatricula(alumno.getMatricula());

        //definicion


        this.addPercentBecaByAlumno(alumnoReporteBecaPeriodo, userLogin);
        return String.format("Se ha calculado correctamnete el porcentaje de beca del alumno %s, parcial %s, periodo %s",
                alumno.getMatricula(), parcial.getParcial(), periodo.getClave());
    }

    @Override
    public void addPercentBecaByAlumno(AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo, UserLogin userLogin) {
        reportPercentBecaDao.addPercentBecaByAlumno(alumnoReporteBecaPeriodo, userLogin);
    }

    @Override
    public WrapperData<AlumnoReporteBecaPeriodo> getAllReportByPeriodo(int page, int pageSize, String cvePeriodo, String palabraClave) {
        return reportPercentBecaDao.getAllReportByPeriodo(page, pageSize, cvePeriodo, palabraClave);
    }

    @Override
    public InputStreamResource exportDataByPeriodoToXLSX(String cvePeriodo) throws IOException {
        WrapperData<AlumnoReporteBecaPeriodo> dataReportByPeriodo = getAllReportByPeriodo(0, -1, cvePeriodo, null);
        DefPorcentajeActividad defPorcentajeActividad = defPorcentajeActividadService.getDefPorcentajeActividades();
        return ExcelWritterReportBeca.export(dataReportByPeriodo, defPorcentajeActividad);
    }

    private String getNombreAlumno(Alumno alumno) {
        return alumno.getNombres() + " " + alumno.getApePaterno() + " " + alumno.getApeMaterno();
    }

    private AlumnoReporteBecaPeriodo getPercentAllParcial(List<ReportPercentActivity> listPercentByParcial,
                                                          DefPorcentajeActividad defPorcentajeActividad) {
        AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo = new AlumnoReporteBecaPeriodo();
        listPercentByParcial.forEach(becaParcial -> {
            if (becaParcial.getParcial() == 1) {

                alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP1(becaParcial.getPorcentajeActividad());
                alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP1(becaParcial.getPorcentajeBiblioteca());
                alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP1(becaParcial.getPorcentajeSala());

            } else if (becaParcial.getParcial() == 2) {
                alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP2(becaParcial.getPorcentajeActividad());
                alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP2(becaParcial.getPorcentajeBiblioteca());
                alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP2(becaParcial.getPorcentajeSala());
            } else if (becaParcial.getParcial() == 3) {
                alumnoReporteBecaPeriodo.setPorcentajeBecaTallerCLubP3(becaParcial.getPorcentajeActividad());
                alumnoReporteBecaPeriodo.setPorcentajeBecaBibliotecaP3(becaParcial.getPorcentajeBiblioteca());
                alumnoReporteBecaPeriodo.setPorcentajeBecaSalaComputoP3(becaParcial.getPorcentajeSala());
            }
            // calcula promedio
            int promedioTaller = calculaPorcentajePromedioParciales(
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaTallerCLubP3());

            int promedioBiblioteca = calculaPorcentajePromedioParciales(
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP3());

            int promedioSala = calculaPorcentajePromedioParciales(
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP1(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP2(),
                    alumnoReporteBecaPeriodo.getPorcentajeBecaBibliotecaP3());


            //promedios alcanzados
            alumnoReporteBecaPeriodo.setPorcentajePromedioTallerClub(promedioTaller);
            alumnoReporteBecaPeriodo.setPorcentajePromedioBiblioteca(promedioBiblioteca);
            alumnoReporteBecaPeriodo.setPorcentajePromedioSala(promedioSala);

            alumnoReporteBecaPeriodo.setPorcentajeLogradoTalleClub(getPorcentajeTalleClub(defPorcentajeActividad, promedioTaller));
            alumnoReporteBecaPeriodo.setPorcentajeLogradoBilioteca(getPorcentajeBiblioteca(defPorcentajeActividad,promedioBiblioteca));
            alumnoReporteBecaPeriodo.setPorcentajeLogradoSala(getPorcentajeSalaComputo(defPorcentajeActividad,promedioSala));

            alumnoReporteBecaPeriodo.setIdActividad(becaParcial.getIdActividad());
        });
        return alumnoReporteBecaPeriodo;
    }

    private int calculaPorcentajePromedioParciales(int percentP1, int percentP2, int percentP3) {
        try {
            return (percentP1 + percentP2 + percentP3) / 3;
        } catch (Exception e) {
            return 0;
        }
    }

    private int getPorcentajeTalleClub(DefPorcentajeActividad defPorcentajeActividad, int promedioParcial) {
        return defPorcentajeActividad.getPorcentajeBecaTaller().calculaPorcentajeBecaPorPorcentajeActividad(promedioParcial);
    }

    private int getPorcentajeSalaComputo(DefPorcentajeActividad defPorcentajeActividad, int promedioParcial) {
        return defPorcentajeActividad.getPorcentajeBecaSala().calculaPorcentajeBecaPorPorcentajeActividad(promedioParcial);
    }

    private int getPorcentajeBiblioteca(DefPorcentajeActividad defPorcentajeActividad, int promedioParcial) {
        return defPorcentajeActividad.getPorcentajeBecaBiblioteca().calculaPorcentajeBecaPorPorcentajeActividad(promedioParcial);
    }
}
