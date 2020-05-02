package edu.calc.becas.mreporte.actividades.percent.activity.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
final class QueriesReportPercentActivity {
    static final String QRY_COUNT_ID_ACTIVIDAD_ALUMNO =
            "SELECT COUNT(1) FROM PORCENTAJE_ACTIVIDADES WHERE ID_ACTIVIDAD_ALUMNO = ? and ID_PARCIAL = ? and CVE_PERIODO = ?";

    static final String QRY_COUNT_DATA_REPORT =
            "SELECT COUNT(1) \n";

    static final String QRY_SELECT_DATA_REPORT =
            "SELECT A.MATRICULA,\n" +
                    "       A.NOMBRES,\n" +
                    "       A.APE_PATERNO,\n" +
                    "       A.APE_MATERNO,\n" +
                    "       PB.PORCENTAJE_SALA,\n" +
                    "       PB.PORCENTAJE_BIBLIOTECA,\n" +
                    "       PB.PORCENTAJE_ACTIVIDAD,\n" +
                    "       AC.ID_ACTIVIDAD,\n" +
                    "       AC.NOMBRE_ACTIVIDAD,\n" +
                    "       P.ID_PARCIAL,\n" +
                    "       P.DESC_PARCIAL,\n" +
                    "       ADP.CVE_LICENCIATURA, \n" +
                    "       ADP.DESC_LICENCIATURA,\n" +
                    "       ADP.CVE_GRUPO,\n" +
                    "       ADP.DESC_GRUPO,\n" +
                    "       ADP.CVE_PERIODO,\n" +
                    "       ADP.DESC_PERIDODO,\n" +
                    "       ADP.ESTATUS\n";

    static final String QRY_ORDER_BY = "\nORDER BY A.NOMBRES ASC, A.APE_PATERNO ASC, A.APE_MATERNO ASC, ADP.CVE_GRUPO ASC, ADP.DESC_LICENCIATURA ASC";

    static final String QRY_FROM_DATA_REPORTE_ACTIVIDADES =
            "FROM PORCENTAJE_ACTIVIDADES PB,\n" +
                    "     PARCIAL_PERIODO PP,\n" +
                    "     PARCIALES P,\n" +
                    "     ACTIVIDAD_ALUMNO AA,\n" +
                    "     ALUMNOS_DAT_PERIODO ADP,\n" +
                    "     ALUMNOS A,\n" +
                    "     HORARIO_ACTIVIDAD HA,\n" +
                    "     ACTIVIDADES AC\n" +
                    "WHERE PB.ID_PARCIAL = PP.ID_PARCIAL\n" +
                    "  AND PP.PARCIAL = P.ID_PARCIAL\n" +
                    "  AND PB.ID_ACTIVIDAD_ALUMNO = AA.ID_ACTIVIDAD_ALUMNO\n" +
                    "  AND AA.ID_ALUMNO_P = ADP.ID_ALUMNOP\n" +
                    "  AND ADP.MATRICULA = A.MATRICULA\n" +
                    "  AND AA.ID_HORARIO_ACTIVIDAD = HA.ID_HORARIO_ACTIVIDAD\n" +
                    "  AND HA.ID_ACTIVIDAD = AC.ID_ACTIVIDAD\n" +
                    "  AND AC.ESTATUS = 'S'\n" +
                    "  AND HA.ESTATUS = 'S'\n" +
                    "  AND A.ESTATUS = 'S'\n" +
                    "  AND AC.TIPO_ACTIVIDAD = 'EX'\n";

    static final String ADD_CONDITION_CICLO_ESCOLAR = "\nAND ADP.CVE_PERIODO = %s\n";
    static final String ADD_CONDITION_LICENCIATURA = "\nAND ADP.CVE_LICENCIATURA = %s\n";
    static final String ADD_CONDITION_GRUPO = "\nAND ADP.CVE_GRUPO = %s\n";
    static final String ADD_CONDITION_ID_PARCIAL = "\nAND P.ID_PARCIAL = %s\n";
    static final String ADD_CONDITION_PARCIAL = "\nAND PP.PARCIAL = %s\n";

    static final String ADD_CONDITION_ACTIVIDAD = "\nAND AC.ID_ACTIVIDAD = %s\n";
    static final String ADD_CONDITION_MATRICULA_ALUMNO = "\nAND A.MATRICULA = %s\n";
    static final String ADD_CONDITION_LIKE_WORD_KEY = "\nAND CONCAT(A.NOMBRES, ' ', A.APE_PATERNO, ' ', A.APE_MATERNO) LIKE %s\n";
    static final String ADD_CONDITION_BY_STATUS = "\nAND ADP.ESTATUS = %s\n";

    static final String QRY_INSERT_PERCENT_ACTIVIDAD =
            "INSERT INTO PORCENTAJE_ACTIVIDADES (ID_ACTIVIDAD_ALUMNO, PORCENTAJE_ACTIVIDAD, ID_PARCIAL, CVE_PERIODO,DESC_PERIDODO, AGREGADO_POR, FECHA_CREACION) " +
                    "VALUE (?, ?, ?, ?, ?, ?, NOW())";
    static final String QRY_UPDATE_PERCENT_ACTIVIDAD =
            "UPDATE PORCENTAJE_ACTIVIDADES SET PORCENTAJE_ACTIVIDAD = ?, ACTUALIZADO_POR = ? WHERE ID_ACTIVIDAD_ALUMNO = ? AND ID_PARCIAL = ?";

    static final String QRY_INSERT_PERCENT_SALA =
            "INSERT INTO PORCENTAJE_ACTIVIDADES (ID_ACTIVIDAD_ALUMNO, PORCENTAJE_SALA, ID_PARCIAL, CVE_PERIODO,DESC_PERIDODO,AGREGADO_POR, FECHA_CREACION) " +
                    "VALUE (?, ?, ?, ?,?,?, NOW())";

    static final String QRY_UPDATE_PERCENT_SALA =
            "UPDATE PORCENTAJE_ACTIVIDADES SET PORCENTAJE_SALA = ?, ACTUALIZADO_POR = ? WHERE ID_ACTIVIDAD_ALUMNO = ? AND ID_PARCIAL = ?";
}



