package edu.calc.becas.mcatalogos.actividades.dao;

final class QueriesActividades {
    private QueriesActividades() {
    }

    static final String QRY_COUNT_ITEM = "SELECT COUNT(1) FROM ACTIVIDADES WHERE 1 = 1\n";

    static String QRY_ACTIVIDADES = "SELECT AC.ID_ACTIVIDAD, AC.NOMBRE_ACTIVIDAD,\n" +
            "       CASE WHEN  AC.OBLIGATORIO = 'S' THEN 'SI' WHEN AC.OBLIGATORIO = 'N' THEN 'NO' END AS OBLIGATORIO,\n" +
            "       AC.ESTATUS " +
            "FROM ACTIVIDADES AC WHERE 1 = 1 AND AC.OBLIGATORIO != 'S'\n";

    public static String QRY_ADD = "INSERT INTO ACTIVIDADES (NOMBRE_ACTIVIDAD, OBLIGATORIO, TIPO_ACTIVIDAD, ESTATUS, AGREGADO_POR, FECHA_CREACION)\n" +
      "VALUES (?, 'N', 'EX','S',  'ADMIN', NOW())";

    static String QRY_GET_ALL = "";

    static final String QRY_CONDITION_ESTATUS_ACTIVIDADES = "\nAND AC.ESTATUS = ? ";
    static final String QRY_CONDITION_ESTATUS_HORARIO_ACTIVIDADES = "\nAND DA.ESTATUS = ? ";

    static final String QRY_DETALLE_ACTIVIDADES = "SELECT A.NOMBRE_ACTIVIDAD, US.ID_USUARIO, " +
            "US.NOMBRES, US.APE_PATERNO , US.APE_MATERNO, DA.* " +
            "FROM HORARIO_ACTIVIDAD DA , ACTIVIDADES A, USUARIOS US " +
            "WHERE DA.ID_ACTIVIDAD = A.ID_ACTIVIDAD " +
            "AND DA.ID_USUARIO = US.ID_USUARIO " ;

    static final String QRY_COUNT_DETALLE_ACTIVIDADES = "SELECT COUNT(1)\n" +
            "FROM HORARIO_ACTIVIDAD DA,\n" +
            "     ACTIVIDADES A,\n" +
            "     USUARIOS US\n" +
            "WHERE DA.ID_ACTIVIDAD = A.ID_ACTIVIDAD\n" +
            "    AND US.ID_USUARIO = DA.ID_USUARIO\n";

    static final String QRY_CONDITION_ID_ACTIVIDAD = "\nAND A.ID_ACTIVIDAD = ?";

    static final String QRY_CONDITION_USERNAME = "\nAND US.USERNAME = ?";

    static final String QRY_LIST_ACTIVIDAD = "SELECT ID_ACTIVIDAD, NOMBRE_ACTIVIDAD FROM ACTIVIDADES";

    static final String QRY_ADD_HORA_ACTIVIDAD = "INSERT INTO HORARIO_ACTIVIDAD (ID_ACTIVIDAD, HORA, AM_PM, " +
            "NUMERO_ALUMNOS, ID_CICLO_ESCOLAR, ID_USUARIO, ESTATUS, AGREGADO_POR, FECHA_CREACION)\n" +
            "VALUES (?,?,?,?,?,?,?,?,CURDATE())";

    static final String QRY_UPDATE_HORA_ACTIVIDAD = "UPDATE HORARIO_ACTIVIDAD SET HORA = ?, AM_PM = ?, " +
            "NUMERO_ALUMNOS = ?, ID_USUARIO = ? WHERE ID_HORARIO_ACTIVIDAD = ?";

    static final String QRY_UPDATE_ACTIVIDAD = "UPDATE ACTIVIDADES SET NOMBRE_ACTIVIDAD = ?, ESTATUS = ? WHERE ID_ACTIVIDAD = ?";

    static final String QRY_ORDER_BY = "\nORDER BY A.ID_ACTIVIDAD, HORA, AM_PM ";
}
