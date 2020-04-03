package edu.calc.becas.mreporte.asistencia.sala.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mreporte.asistencia.sala.dao.AsistenciaSalaDao;
import edu.calc.becas.mreporte.asistencia.sala.model.AlumnoAsistenciaSala;
import edu.calc.becas.mreporte.asistencia.sala.model.FechaAsistencia;
import edu.calc.becas.mreporte.asistencia.sala.model.PaseAsistencia;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.UtilDate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AsistenciaSalaServiceImpl implements AsistenciaSalaService {

    private final AsistenciaSalaDao asistenciaSalaDao;
    private final ParcialService parcialService;
    private final UsuarioService usuarioService;

    public AsistenciaSalaServiceImpl(AsistenciaSalaDao asistenciaSalaDao, ParcialService parcialService, UsuarioService usuarioService) {
        this.asistenciaSalaDao = asistenciaSalaDao;
        this.parcialService = parcialService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<AlumnoAsistenciaSala> getAlumnosByScheduleAndUser(String username, String idHorario, List<FechaAsistencia> fechasAsistencia) throws GenericException {
        Parcial parcialActual = parcialService.getParcialActual();
        Parcial parcialAnterior = parcialService.getParcialAnterior(parcialActual);
        Usuario usuario = usuarioService.getByUsername(username);
        addDateToDateEndParcial(parcialActual, usuario);
        return asistenciaSalaDao.getAlumnosByScheduleAndUser(usuario, idHorario, fechasAsistencia, parcialActual, parcialAnterior);
    }

    private void addDateToDateEndParcial(Parcial parcialActual, Usuario usuario) {

        try {
            Date date = UtilDate.convertToDate(parcialActual.getFechaFin(), UtilDate.PATTERN_DIAG);
            Date dateEnd = UtilDate.getNextDayBySum(date, usuario.getDiasRetrocesoReporte());
            String dateEndString = UtilDate.convertDateToString(dateEnd, UtilDate.PATTERN_DIAG);
            parcialActual.setFechaFin(dateEndString);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public List<PaseAsistencia> addPresenceByDate(List<PaseAsistencia> asistencias, Usuario usuario) throws GenericException {
        return asistenciaSalaDao.addPresenceByDate(asistencias, usuario);
    }

}
