package edu.calc.becas.mcatalogos.actividades.dao;

final class QueriesActividades {
    private QueriesActividades() {
    }

    static final String QRY_COUNT_ITEM = "SELECT COUNT(1) FROM ACTIVIDADES";

    static String QRY_ACTIVIDADES = "SELECT AC.ID_ACTIVIDAD, AC.NOMBRE_ACTIVIDAD,\n" +
            "       CASE WHEN  AC.OBLIGATORIO = 'S' THEN 'SI' WHEN AC.OBLIGATORIO = 'N' THEN 'NO' END AS OBLIGATORIO,\n" +
            "       AC.ESTATUS " +
            "FROM ACTIVIDADES AC";

    public static String QRY_ADD = "INSERT INTO CICLO_ESCOLAR (DESCRIPCION_CICLO, PERIODO_ACTUAL, ESTATUS, AGREGADO_POR," +
            " FECHA_CREACION)\n" +
          "VALUES ('2019-2019A','S','S','admin',CURDATE());";

    static String QRY_GET_ALL = "";

    static final String QRY_CONDITION_ESTATUS = " AND ESTATUS = ? ";

    static final String QRY_DETALLE_ACTIVIDADES = "SELECT A.NOMBRE_ACTIVIDAD, CI.DESCRIPCION_CICLO, US.ID_USUARIO, " +
            "US.NOMBRES, US.APE_PATERNO , US.APE_MATERNO, DA.* " +
            "FROM HORARIO_ACTIVIDAD DA , ACTIVIDADES A, CICLO_ESCOLAR CI, USUARIOS US " +
            "WHERE DA.ID_ACTIVIDAD = A.ID_ACTIVIDAD " +
            "AND DA.ID_CICLO_ESCOLAR = CI.ID_CICLO_ESCOLAR " +
            "AND DA.ID_USUARIO = US.ID_USUARIO";

    static final String QRY_COUNT_DETALLE_ACTIVIDADES = "SELECT COUNT(1) FROM HORARIO_ACTIVIDAD DA , " +
            "ACTIVIDADES A WHERE DA.ID_ACTIVIDAD = A.ID_ACTIVIDAD\n";

    static final String QRY_CONDITION_ID_ACTIVIDAD = " AND A.ID_ACTIVIDAD = ?";

    static final String QRY_LIST_ACTIVIDAD = "SELECT ID_ACTIVIDAD, NOMBRE_ACTIVIDAD FROM ACTIVIDADES";

    static final String QRY_ADD_HORA_ACTIVIDAD = "INSERT INTO HORARIO_ACTIVIDAD (ID_ACTIVIDAD, HORA, AM_PM, " +
            "NUMERO_ALUMNOS, ID_CICLO_ESCOLAR, ID_USUARIO, ESTATUS, AGREGADO_POR, FECHA_CREACION)\n" +
            "VALUES (?,?,?,?,?,?,?,?,CURDATE())";
}
