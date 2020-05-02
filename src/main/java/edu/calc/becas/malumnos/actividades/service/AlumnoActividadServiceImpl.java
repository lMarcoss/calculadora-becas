package edu.calc.becas.malumnos.actividades.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.actividades.model.ActividadAlumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.actividades.asistencia.service.AsistenciaService;
import edu.calc.becas.mreporte.actividades.percent.activity.service.ReportPercentActivitiesService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.UtilDate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: servicios para consulta de actividades por alumnos
 * Date: 2019-06-16
 */
@Service
public class AlumnoActividadServiceImpl implements AlumnoActividadService {

    private final AlumnoActividadDao alumnoActividadDao;
    private final CicloEscolarService cicloEscolarService;
    private final ParcialService parcialService;
    private final AsistenciaService asistenciaService;
    private final ReportPercentActivitiesService reportPercentActivitiesService;
    private final UsuarioService usuarioService;

    public AlumnoActividadServiceImpl(AlumnoActividadDao alumnoActividadDao, CicloEscolarService cicloEscolarService,
                                      ParcialService parcialService, AsistenciaService asistenciaService,
                                      ReportPercentActivitiesService reportPercentActivitiesService, UsuarioService usuarioService) {
        this.alumnoActividadDao = alumnoActividadDao;
        this.cicloEscolarService = cicloEscolarService;
        this.parcialService = parcialService;
        this.asistenciaService = asistenciaService;
        this.reportPercentActivitiesService = reportPercentActivitiesService;
        this.usuarioService = usuarioService;
    }

    @Override
    public ActividadVo getActividadByAlumno(String matricula, CicloEscolarVo cicloEscolarActual) {
        return alumnoActividadDao.getActividadByAlumno(matricula, cicloEscolarActual);
    }

    @Override
    public WrapperData getAllAlumnosByActividad(int page, int pageSize, String idActividad, String idCiclo) {

        return alumnoActividadDao.getAllAlumnosByActividad(page, pageSize, idActividad, idCiclo);
    }


}
