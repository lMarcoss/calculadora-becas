package edu.calc.becas.malumnos.dao;

public class QueriesAlumnos {


    static final String QRY_GET_ALL = "SELECT AL.*, AA.ID_ACTIVIDAD\n" +
            "FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO ADP, ACTIVIDAD_ALUMNO AA\n" +
            "WHERE AL.MATRICULA = ADP.MATRICULA\n" +
            "  AND ADP.CVE_PERIODO = '1819A'\n" +
            "  AND ADP.CVE_GRUPO = AA.CVE_GRUPO\n" +
            "  AND ADP.CVE_LICENCIATURA = AA.CVE_LICENCIATURA\n" +
            "  AND ADP.CVE_PERIODO = AA.CVE_PERIODO\n" +
            "  AND AL.ESTATUS = 'S'\n" +
            "  AND AA.ID_ACTIVIDAD = 2\n";

    static final String QRY_CONDITION_MATRICULA = "\nAND AL.MATRICULA = ?";
    static final String QRY_CONDITION_ESTATUS = " AND AL.ESTATUS = ? ";
    static final String QRY_CONDITION_ACTIVIDAD = "AND ACAL.ID_ACTIVIDAD = ? ";
    static final String QRY_ORDER_BY_NOMBRE_ALUMNO = "\nORDER BY APE_PATERNO, APE_MATERNO, NOMBRES ASC\n";

    static final String QRY_ADD = "INSERT INTO ALUMNOS (ID_ALUMNO, CURP, MATRICULA, NOMBRES, APE_PATERNO, APE_MATERNO, ESTATUS, FECHA_CREACION, AGREGADO_POR) VALUES\n" +
            "(?,?,?,?,?,?,?,CURDATE(),?)";

    static final String QRY_ADD_ALUMNO_ACTIVIDAD = "INSERT INTO ACTIVIDAD_ALUMNO (ID_HORARIO_ACTIVIDAD,ID_ALUMNO_P, AGREGADO_POR, FECHA_CREACION)\n" +
      "VALUES (?,(SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?),'ADMIN', NOW())";

    static final String QRY_ADD_ALUMNO_ACTIVIDADI = "INSERT INTO ACTIVIDAD_ALUMNO (ID_ACTIVIDAD, MATRICULA, AGREGADO_POR, FECHA_CREACION) VALUES\n" +
            "(?, ?,'ADMIN',CURDATE())";

    static final String QRY_ID_ALUMNO = "SELECT max(ID_ALUMNO)+1 FROM ALUMNOS";

    static final String QRY_EXISTE_ALUMNO = "SELECT COUNT(*) FROM ALUMNOS WHERE MATRICULA = ?";

    static final String QRY_EXISTE_ALUMNO_PERIODO = "SELECT count(*) FROM ALUMNOS_DAT_PERIODO WHERE CVE_PERIODO = ? AND MATRICULA = ?";

    static final String QRY_ADD_ALUMNO_PERIODO = "INSERT INTO ALUMNOS_DAT_PERIODO (MATRICULA,CVE_GRUPO, DESC_GRUPO, CVE_LICENCIATURA, DESC_LICENCIATURA, CVE_PERIODO, DESC_PERIDODO) VALUES\n" +
      "(?,?,?,?,?,?,?);";

    static final String QRY_ALUMNOS_CARGAS =
            "SELECT ID_ALUMNO,\n" +
                    "       AL.MATRICULA,\n" +
                    "       CURP,\n" +
                    "       NOMBRES,\n" +
                    "       APE_PATERNO,\n" +
                    "       APE_MATERNO,\n" +
                    "       CVE_GRUPO,\n" +
                    "       DESC_GRUPO," +
                    "       AL.CODIGO_RFID,\n" +
                    "       ALD.CVE_LICENCIATURA,\n" +
                    "       ALD.DESC_LICENCIATURA,\n" +
                    "       AL.ESTATUS \n" +
                    "FROM ALUMNOS AL\n" +
      "INNER JOIN ALUMNOS_DAT_PERIODO ALD\n" +
      "ON AL.MATRICULA = ALD.MATRICULA\n" +
      "AND CVE_PERIODO  = ?";


    static final String QRY_CONDITION_LIC =" AND CVE_LICENCIATURA = ? ";

    static final String QRY_CONDITION_GRUPO =" AND CVE_GRUPO = ? ";

    static final String QRY_EXISTE_ALUMNO_ACTIVIDAD = "SELECT count(1) FROM ACTIVIDAD_ALUMNO WHERE ID_ALUMNO_P IN (SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?)";

    static final String QRY_UPD_ALUMNO_ACTIVIDAD ="UPDATE ACTIVIDAD_ALUMNO SET ID_HORARIO_ACTIVIDAD = ? WHERE ID_ALUMNO_P = (SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?)";
}
