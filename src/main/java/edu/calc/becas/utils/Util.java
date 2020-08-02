package edu.calc.becas.utils;

import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.Date;

import static edu.calc.becas.utils.UtilDate.PATTERN_DIAG;

/**
 * Valida si un parcil ha finalizado de acuerdo a los dias de tolerancia de cada usuario
 *
 * @author Marcos Santiago Leonardo
 * Date: 09/04/20
 */
public final class Util {
    private Util() {
    }

    public static boolean parcialFinalizado(Parcial parcial, Usuario usuario) throws Exception {
        Date today = UtilDate.getDateToday();
        Date fechaFinParcial = UtilDate.convertToDate(parcial.getFechaFin(), PATTERN_DIAG);

        Date fechaFinParcialTolerancia;
        if (usuario.getDiasRetrocesoReporte() > 0) {
            fechaFinParcialTolerancia = UtilDate.getDateSumDay(fechaFinParcial, usuario.getDiasRetrocesoReporte());
        } else {
            fechaFinParcialTolerancia = fechaFinParcial;
        }

        return !fechaFinParcialTolerancia.before(today);


    }
}
