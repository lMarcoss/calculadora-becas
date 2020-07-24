package edu.calc.becas.mreporte.actividades.asistencia.dao;

/**
 * Define las consultas o actualizaciones en la BD
 */
final class QueriesAsistenciaSala {
    private QueriesAsistenciaSala() {
    }

    static final String GET_ALUMNOS_BY_USER_AND_SCHEDULE = "SELECT U.DIAS_RETROCESO_REPORTE, AA.ID_ACTIVIDAD_ALUMNO, U.ID_USUARIO, A.MATRICULA, A.NOMBRES,  A.APE_PATERNO, A.APE_MATERNO, HA.ID_HORARIO_ACTIVIDAD\n" +
            "FROM USUARIOS U, HORARIO_ACTIVIDAD HA, ACTIVIDAD_ALUMNO AA, ALUMNOS_DAT_PERIODO AP, ALUMNOS A\n" +
            "WHERE U.ESTATUS = 'S'\n" +
            "  AND U.ID_USUARIO = HA.ID_USUARIO\n" +
            "  AND HA.ID_HORARIO_ACTIVIDAD = ?\n" +
            "  AND HA.ID_HORARIO_ACTIVIDAD = AA.ID_HORARIO_ACTIVIDAD\n" +
            "  AND HA.ESTATUS = 'S'\n" +
            "  AND AA.ID_ALUMNO_P = AP.ID_ALUMNOP\n" +
            "  AND AP.MATRICULA = A.MATRICULA\n";


    static final String QRY_GET_ASISTENCIA_BY_ACTIVIDAD_ALUMNO = "SELECT ASISTENCIA FROM ASISTENCIA_ACTIVIDADES WHERE  ID_ACTIVIDAD_ALUMNO = ? AND FECHA_ASISTENCIA = STR_TO_DATE(?, '%d/%m/%Y')\n";
    static final String QRY_ADD_PRESENCE = "INSERT INTO ASISTENCIA_ACTIVIDADES (ID_ACTIVIDAD_ALUMNO, ASISTENCIA, FECHA_ASISTENCIA, AGREGADO_POR, FECHA_CREACION) VALUES (?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?, now())\n";
    static final String QRY_UPDATE_PRESENCE = "UPDATE ASISTENCIA_ACTIVIDADES SET ASISTENCIA = ?, ACTUALIZADO_POR = ?, FECHA_ACTUALIZACION = NOW() WHERE ID_ACTIVIDAD_ALUMNO = ? AND FECHA_ASISTENCIA = STR_TO_DATE(?, '%d/%m/%Y')\n";
    static final String QRY_COUNT_ASISTENCIAS =
            "SELECT COUNT(1) ASISTENCIAS\n" +
                    "FROM ASISTENCIA_ACTIVIDADES\n" +
                    "WHERE ID_ACTIVIDAD_ALUMNO = ? AND FECHA_ASISTENCIA BETWEEN ? AND ?\n" +
                    "  AND ASISTENCIA = '.'";

}
