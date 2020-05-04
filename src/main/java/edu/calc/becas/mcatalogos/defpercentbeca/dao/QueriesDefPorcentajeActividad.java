package edu.calc.becas.mcatalogos.defpercentbeca.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
public final class QueriesDefPorcentajeActividad {
    private QueriesDefPorcentajeActividad() {
    }

    public static String QRY_GET_ACTIVIDAD_POR_TIPO =
            "SELECT CVE_TIPO_ACTIVIDAD,\n" +
                    "       DS_ACTIVIDAD,\n" +
                    "       PORCENTAJE_MINIMO_REQUERIDO,\n" +
                    "       PORCENTAJE_BECA_ACT_INCUMPLIDA,\n" +
                    "       PORCENTAJE_BECA_ACT_CUMPLIDA\n" +
                    "FROM DEF_PORCENTAJE_TIPO_ACTIVIDAD WHERE CVE_TIPO_ACTIVIDAD = ?";
}
