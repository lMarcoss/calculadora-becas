package edu.calc.becas.mreporte.actividades.percent.beca.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
final class QueriesReportPercentBeca {
    private QueriesReportPercentBeca() {
    }

    static String QRY_ADD_PERCENT_BECA =
            "INSERT INTO PORCENTAJE_BECA (MATRICULA, NOMBRES, CVE_GRUPO, DESC_GRUPO, CVE_LICENCIATURA, DESC_LICENCIATURA,\n" +
                    "                             CVE_PERIODO, DESC_PERIDODO, ID_ACTIVIDAD, DS_ACTIVIDAD, PORCENTAJE_TALLER_CLUB_P1,\n" +
                    "                             PORCENTAJE_TALLER_CLUB_P2, PORCENTAJE_TALLER_CLUB_P3, PORCENTAJE_BIBLIOTECA_P1,\n" +
                    "                             PORCENTAJE_BIBLIOTECA_P2, PORCENTAJE_BIBLIOTECA_P3, PORCENTAJE_SALA_COMPUTO_P1,\n" +
                    "                             PORCENTAJE_SALA_COMPUTO_P2, PORCENTAJE_SALA_COMPUTO_P3, AGREGADO_POR, FECHA_CREACION)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
    static String QRY_UPDATE_PERCENT_BECA =
            "UPDATE PORCENTAJE_BECA\n" +
                    "SET NOMBRES                    = ?,\n" +
                    "    CVE_GRUPO                  = ?,\n" +
                    "    DESC_GRUPO                 = ?,\n" +
                    "    CVE_LICENCIATURA           = ?,\n" +
                    "    DESC_LICENCIATURA          = ?,\n" +
                    "    DESC_PERIDODO              = ?,\n" +
                    "    ID_ACTIVIDAD               = ?,\n" +
                    "    DS_ACTIVIDAD               = ?,\n" +
                    "    PORCENTAJE_TALLER_CLUB_P1  = ?,\n" +
                    "    PORCENTAJE_TALLER_CLUB_P2  = ?,\n" +
                    "    PORCENTAJE_TALLER_CLUB_P3  = ?,\n" +
                    "    PORCENTAJE_BIBLIOTECA_P1   = ?,\n" +
                    "    PORCENTAJE_BIBLIOTECA_P2   = ?,\n" +
                    "    PORCENTAJE_BIBLIOTECA_P3   = ?,\n" +
                    "    PORCENTAJE_SALA_COMPUTO_P1 = ?,\n" +
                    "    PORCENTAJE_SALA_COMPUTO_P2 = ?,\n" +
                    "    PORCENTAJE_SALA_COMPUTO_P3 = ?," +
                    "    ACTUALIZADO_POR = ?,\n" +
                    "    FECHA_ACTUALIZACION = NOW()\n" +
                    "where MATRICULA = ?\n" +
                    "  and CVE_PERIODO = ?";

    static String QRY_VALIDATE_MATRICULA_EXISTS =
            "SELECT COUNT(1) MATRICULA\n" +
                    "FROM PORCENTAJE_BECA\n" +
                    "WHERE MATRICULA = ?\n" +
                    "  AND CVE_PERIODO = ?";

    static String QRY_GET_ALL_REPORTE_BECA =
            "SELECT MATRICULA,\n" +
                    "       NOMBRES,\n" +
                    "       CVE_GRUPO,\n" +
                    "       DESC_GRUPO,\n" +
                    "       CVE_LICENCIATURA,\n" +
                    "       DESC_LICENCIATURA,\n" +
                    "       CVE_PERIODO,\n" +
                    "       DESC_PERIDODO,\n" +
                    "       ID_ACTIVIDAD,\n" +
                    "       DS_ACTIVIDAD,\n" +
                    "       PORCENTAJE_TALLER_CLUB_P1,\n" +
                    "       PORCENTAJE_TALLER_CLUB_P2,\n" +
                    "       PORCENTAJE_TALLER_CLUB_P3,\n" +
                    "       PORCENTAJE_BIBLIOTECA_P1,\n" +
                    "       PORCENTAJE_BIBLIOTECA_P2,\n" +
                    "       PORCENTAJE_BIBLIOTECA_P3,\n" +
                    "       PORCENTAJE_SALA_COMPUTO_P1,\n" +
                    "       PORCENTAJE_SALA_COMPUTO_P2,\n" +
                    "       PORCENTAJE_SALA_COMPUTO_P3\n" +
                    "FROM PORCENTAJE_BECA\n" +
                    "WHERE CVE_PERIODO = ?\n";

    static String QRY_COUNT_ALL_BY_PERIODO =
            "SELECT COUNT(1)\n" +
                    "FROM PORCENTAJE_BECA\n" +
                    "WHERE CVE_PERIODO =?";
    static String QRY_PAGEABLE =
            "\nLIMIT ? OFFSET ? \n";
}
