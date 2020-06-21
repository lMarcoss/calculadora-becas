package edu.calc.becas.mconfiguracion.cicloescolar.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 10/03/20
 */
final class QueriesCicloEscolar {
    private QueriesCicloEscolar() {
    }

    static final String QRY_GET_ALL = "SELECT DISTINCT CVE_PERIODO, DESC_PERIDODO FROM ALUMNOS_DAT_PERIODO";
}
