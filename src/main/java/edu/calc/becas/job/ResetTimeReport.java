package edu.calc.becas.job;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.UtilDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.common.utils.Constant.TIPO_USUARIO_DEFAULT;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * 14/03/21
 */
@Slf4j
@Component
public class ResetTimeReport {

    private final UsuarioService usuarioService;

    public ResetTimeReport(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Resetea el numero de dias de tolerancia para subir reportes a cada usuario a 3 si el dia configurado ya supero la fecha actual
     */
    @Scheduled(fixedRate = 7200000)// ejecuta el  cron cada 2hrs
    public void cronJobSch() {
        WrapperData<Usuario> usuarioWrapperData = usuarioService.getAllByStatusAndOneParam(0, Integer.parseInt(ITEMS_FOR_PAGE), "S", TIPO_USUARIO_DEFAULT);

        usuarioWrapperData.getData().forEach(usuario -> {
            if (toleranceDateIsOutDate(usuario)) {
                usuario.setPassword(null);
                usuario.setDiasRetrocesoReporte(3);
                usuario.setActualizadoPor("ADMIN_SCHEDULED");
                usuarioService.update(usuario);
            }
        });
    }

    private boolean toleranceDateIsOutDate(Usuario usuario) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime toleranceDate = UtilDate.getDateSumDay(usuario.getFechaRegistroDiasRetroceso(), usuario.getDiasRetrocesoReporte());
        log.info("hoy - " + today + " -registrado: - " + usuario.getFechaRegistroDiasRetroceso() + " - sumado - " + toleranceDate.toString() + " : " + toleranceDate.isBefore(today));
        return !toleranceDate.isBefore(today);
    }
}
