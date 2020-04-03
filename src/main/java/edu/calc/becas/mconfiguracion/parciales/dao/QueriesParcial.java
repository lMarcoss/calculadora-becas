package edu.calc.becas.mconfiguracion.parciales.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
final class QueriesParcial {
    private QueriesParcial() {
    }

    static final String QRY_GET_ALL_CATALOGUE =
            "SELECT ID_PARCIAL, DESC_PARCIAL FROM PARCIALES\n";

    static final String QRY_GET_ALL =
            "SELECT PP.ID_PARCIAL, PP.PARCIAL, P.DESC_PARCIAL, PARCIAL_ACTUAL,\n" +
                    "       DATE_FORMAT(PP.FECHA_INICIO, '%d/%m/%Y') AS FECHA_INICIO,\n" +
                    "       DATE_FORMAT(PP.FECHA_FIN, '%d/%m/%Y') AS FECHA_FIN,\n" +
                    "       PP.CVE_PERIODO, PP.DESC_PERIODO, PP.TOTAL_HORAS_BIBLIOTECA, PP.TOTAL_ASISTENCIA_SALA\n" +
                    "FROM PARCIAL_PERIODO PP,\n" +
                    "     PARCIALES P\n" +
                    "WHERE PP.PARCIAL = P.ID_PARCIAL AND CVE_PERIODO = ?\n";
    static final String QRY_GET_PARCIAL_ACTUAL = "SELECT PP.ID_PARCIAL, PARCIAL, DESC_PARCIAL, PARCIAL_ACTUAL,\n" +
            "       DATE_FORMAT(FECHA_INICIO, '%d/%m/%Y') AS FECHA_INICIO,\n" +
            "       DATE_FORMAT(FECHA_FIN, '%d/%m/%Y') AS FECHA_FIN,\n" +
            "       CVE_PERIODO, DESC_PERIODO, PP.TOTAL_HORAS_BIBLIOTECA, PP.TOTAL_ASISTENCIA_SALA\n" +
            "FROM PARCIAL_PERIODO PP,\n" +
            "     PARCIALES P\n" +
            "WHERE PP.PARCIAL = P.ID_PARCIAL AND PARCIAL_ACTUAL = 'S' AND PP.CVE_PERIODO = ?\n";

    static final String QRY_GET_PARCIAL_ANTERIOR = "SELECT PP.ID_PARCIAL, PARCIAL, DESC_PARCIAL, PARCIAL_ACTUAL,\n" +
            "                   DATE_FORMAT(FECHA_INICIO, '%d/%m/%Y') AS FECHA_INICIO,\n" +
            "                   DATE_FORMAT(FECHA_FIN, '%d/%m/%Y') AS FECHA_FIN,\n" +
            "                   CVE_PERIODO, DESC_PERIODO, PP.TOTAL_HORAS_BIBLIOTECA, PP.TOTAL_ASISTENCIA_SALA\n" +
            "            FROM PARCIAL_PERIODO PP,\n" +
            "                 PARCIALES P\n" +
            "            WHERE PP.PARCIAL = P.ID_PARCIAL AND PP.CVE_PERIODO = ? and PP.ID_PARCIAL = ?\n";
    static final String QRY_INACTIVE_ESTATUS = "UPDATE PARCIAL_PERIODO SET PARCIAL_ACTUAL = 'N' WHERE 1=1";
    static final String QRY_ACTIVE_ESTATUS = "UPDATE PARCIAL_PERIODO SET PARCIAL_ACTUAL = 'S' WHERE ID_PARCIAL = ?";
    static final String QRY_ADD =
            "INSERT INTO PARCIAL_PERIODO\n" +
                    "(PARCIAL, PARCIAL_ACTUAL, FECHA_INICIO, FECHA_FIN, CVE_PERIODO, DESC_PERIODO, AGREGADO_POR,\n" +
                    " FECHA_CREACION, TOTAL_HORAS_BIBLIOTECA,TOTAL_ASISTENCIA_SALA )\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(),?,?)";

    static final String QRY_UPDATE =
            "UPDATE PARCIAL_PERIODO\n" +
                    "SET PARCIAL             = ?,\n" +
                    "    PARCIAL_ACTUAL      = ?,\n" +
                    "    FECHA_INICIO        =?,\n" +
                    "    FECHA_FIN           = ?,\n" +
                    "    CVE_PERIODO         = ?,\n" +
                    "    DESC_PERIODO        = ?,\n" +
                    "    ACTUALIZADO_POR= ?,\n" +
                    "    FECHA_ACTUALIZACION =NOW(),\n" +
                    "    TOTAL_HORAS_BIBLIOTECA        = ?,\n" +
                    "    TOTAL_ASISTENCIA_SALA        = ?\n" +
                    "WHERE ID_PARCIAL = ?";

}
