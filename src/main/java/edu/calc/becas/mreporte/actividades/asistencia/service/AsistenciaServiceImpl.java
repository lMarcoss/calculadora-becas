package edu.calc.becas.mreporte.actividades.asistencia.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.actividades.asistencia.dao.AsistenciaDao;
import edu.calc.becas.mreporte.actividades.asistencia.model.AlumnoAsistenciaTaller;
import edu.calc.becas.mreporte.actividades.asistencia.model.FechaAsistencia;
import edu.calc.becas.mreporte.actividades.asistencia.model.PaseAsistencia;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.UtilDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementa los servicios de administracion de asistencias a talleres
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 */
@Service
@Slf4j
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaDao asistenciaDao;
    private final ParcialService parcialService;
    private final UsuarioService usuarioService;

    public AsistenciaServiceImpl(AsistenciaDao asistenciaDao, ParcialService parcialService, UsuarioService usuarioService) {
        this.asistenciaDao = asistenciaDao;
        this.parcialService = parcialService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<AlumnoAsistenciaTaller> getAlumnosByScheduleAndUser(UserLogin userLogin, String idHorario, List<FechaAsistencia> fechasAsistencia) throws GenericException {
        Parcial parcialActual = parcialService.getParcialActual();
        Parcial parcialAnterior = parcialService.getParcialAnterior(parcialActual);
        Usuario usuario = usuarioService.getByUsername(userLogin.getUsername());
        addDateToDateEndParcial(parcialActual, usuario);
        return asistenciaDao.getAlumnosByScheduleAndUser(usuario, idHorario, fechasAsistencia, parcialActual, parcialAnterior);
    }

    /**
     * Define la fecha final en la que se permite editar las asistencias del parcial
     * de acuerdo a los dias de tolereancia del usuarioo
     *
     * @param parcialActual parcial en curso
     * @param usuario       usuario o encargado de la actividad
     */
    private void addDateToDateEndParcial(Parcial parcialActual, Usuario usuario) {

        try {
            Date dateToday = UtilDate.getDateToday();


            Date date = UtilDate.convertToDate(parcialActual.getFechaFin(), UtilDate.PATTERN_DIAG);
            Date dateEnd = UtilDate.getDateSumDay(date, usuario.getDiasRetrocesoReporte());


            Date fechaInicio = UtilDate.convertToDate(parcialActual.getFechaInicio(), UtilDate.PATTERN_DIAG);
            if (!UtilDate.isDateBetween(fechaInicio, date, dateToday)) {
                String dateEndString = UtilDate.convertDateToString(dateEnd, UtilDate.PATTERN_DIAG);
                parcialActual.setFechaFin(dateEndString);
            }


        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuarioRequest) throws GenericException {
        Parcial parcialActual = parcialService.getParcialActual();
        Parcial parcialAnterior = parcialService.getParcialAnterior(parcialActual);
        Usuario usuario = usuarioService.getByUsername(usuarioRequest.getUsername());
        addDateToDateEndParcial(parcialActual, usuario);
        return asistenciaDao.addPresenceByDate(asistencias, usuario, parcialActual, parcialAnterior);
    }

    @Override
    public int countPresenceByActivityAlumno(Integer idActividadAlumno, String fechaInicio, String fechaFin) {
        return asistenciaDao.countPresenceByActivityAlumno(idActividadAlumno, fechaInicio, fechaFin);
    }

}
