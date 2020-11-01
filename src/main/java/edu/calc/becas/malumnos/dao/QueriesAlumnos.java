package edu.calc.becas.malumnos.dao;

interface QueriesAlumnos {


    String QRY_GET_ALL = "SELECT AL.*, AA.ID_ACTIVIDAD\n" +
            "FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO ADP, ACTIVIDAD_ALUMNO AA\n" +
            "WHERE AL.MATRICULA = ADP.MATRICULA\n" +
            "  AND ADP.CVE_PERIODO = '1819A'\n" +
            "  AND ADP.CVE_GRUPO = AA.CVE_GRUPO\n" +
            "  AND ADP.CVE_LICENCIATURA = AA.CVE_LICENCIATURA\n" +
            "  AND ADP.CVE_PERIODO = AA.CVE_PERIODO\n" +
            "  AND AL.ESTATUS = 'S'\n" +
            "  AND AA.ID_ACTIVIDAD = 2\n";

    String QRY_CONDITION_MATRICULA = "\nAND AL.MATRICULA = ?";
    String QRY_CONDITION_ESTATUS = " AND AL.ESTATUS = ? ";
    String QRY_CONDITION_ACTIVIDAD = "AND ACAL.ID_ACTIVIDAD = ? ";
    String QRY_ORDER_BY_NOMBRE_ALUMNO = "\nORDER BY APE_PATERNO, APE_MATERNO, NOMBRES ASC\n";

    String QRY_ADD = "INSERT INTO ALUMNOS (ID_ALUMNO, CURP, MATRICULA, NOMBRES, APE_PATERNO, APE_MATERNO, ESTATUS, FECHA_CREACION, AGREGADO_POR) VALUES\n" +
            "(?,?,?,?,?,?,?,CURDATE(),?)";

    String QRY_ADD_ALUMNO_ACTIVIDAD = "INSERT INTO ACTIVIDAD_ALUMNO (ID_HORARIO_ACTIVIDAD,ID_ALUMNO_P, AGREGADO_POR, FECHA_CREACION)\n" +
            "VALUES (?,(SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?),'ADMIN', NOW())";

    String QRY_ADD_ALUMNO_ACTIVIDADI = "INSERT INTO ACTIVIDAD_ALUMNO (ID_ACTIVIDAD, MATRICULA, AGREGADO_POR, FECHA_CREACION) VALUES\n" +
            "(?, ?,'ADMIN',CURDATE())";

    String QRY_ID_ALUMNO = "SELECT max(ID_ALUMNO)+1 FROM ALUMNOS";

    String QRY_EXISTE_ALUMNO = "SELECT COUNT(*) FROM ALUMNOS WHERE MATRICULA = ?";

    String QRY_EXISTE_ALUMNO_PERIODO = "SELECT count(*) FROM ALUMNOS_DAT_PERIODO WHERE CVE_PERIODO = ? AND MATRICULA = ?";

    String QRY_ADD_ALUMNO_PERIODO = "INSERT INTO ALUMNOS_DAT_PERIODO (MATRICULA,CVE_GRUPO, DESC_GRUPO, CVE_LICENCIATURA, DESC_LICENCIATURA, CVE_PERIODO, DESC_PERIDODO) VALUES\n" +
            "(?,?,?,?,?,?,?);";

    String QRY_ALUMNOS_CARGAS =
            "SELECT         ID_ALUMNO,\n" +
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
                    "       ALD.ESTATUS, \n" +
                    "       ALD.ID_ALUMNOP \n" +
                    "FROM ALUMNOS AL\n" +
                    "INNER JOIN ALUMNOS_DAT_PERIODO ALD\n" +
                    "ON AL.MATRICULA = ALD.MATRICULA\n" +
                    "AND CVE_PERIODO  = ?";


    String QRY_CONDITION_LIC = " AND CVE_LICENCIATURA = ? ";

    String QRY_CONDITION_GRUPO = " AND CVE_GRUPO = ? ";

    String QRY_EXISTE_ALUMNO_ACTIVIDAD = "SELECT count(1) FROM ACTIVIDAD_ALUMNO WHERE ID_ALUMNO_P IN (SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?)";

    String QRY_UPD_ALUMNO_ACTIVIDAD = "UPDATE ACTIVIDAD_ALUMNO SET ID_HORARIO_ACTIVIDAD = ? WHERE ID_ALUMNO_P = (SELECT ID_ALUMNOP FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?)";

    String QRY_UPDATE_ALUMNO_PERIODO =
            "UPDATE ALUMNOS_DAT_PERIODO\n" +
                    "SET CVE_GRUPO         = ?,\n" +
                    "    DESC_GRUPO        = ?,\n" +
                    "    CVE_LICENCIATURA  = ?,\n" +
                    "    DESC_LICENCIATURA = ?,\n" +
                    "    ESTATUS           = ?\n" +
                    "WHERE ID_ALUMNOP = ?";

    String QRY_UPDATE_DATOS_ALUMNO =
            "UPDATE ALUMNOS\n" +
                    "SET MATRICULA           = ?,\n" +
                    "    CODIGO_RFID         = ?,\n" +
                    "    CURP                = ?,\n" +
                    "    NOMBRES             = ?,\n" +
                    "    APE_PATERNO         = ?,\n" +
                    "    APE_MATERNO         = ?,\n" +
                    "    ACTUALIZADO_POR     =?,\n" +
                    "    FECHA_ACTUALIZACION = NOW()\n" +
                    "WHERE ID_ALUMNO = ?";

    String QRY_INFO_ALUMNO =
            "SELECT ID_ALUMNO,ALM.NOMBRES,ALM.APE_PATERNO,ALM.APE_MATERNO, '3' ID_ROL, 'ALUMNO' ROL, ALM.MATRICULA as USERNAME, CURP COMMONVAL01, AL.CVE_GRUPO COMMONVAL02,\n" +
                    "    AL.DESC_LICENCIATURA COMMONVAL03,\n" +
                    "             AT.NOMBRE_ACTIVIDAD COMMONVAL04,\n" +
                    "             Concat ( AC.HORA,':',AC.AM_PM) COMMONVAL05,\n" +
                    "       ALM.CODIGO_RFID COMMONVAL06\n" +
                    "      FROM ALUMNOS ALM, HORARIO_ACTIVIDAD AC, ACTIVIDAD_ALUMNO CAL, ALUMNOS_DAT_PERIODO AL, ACTIVIDADES AT\n" +
                    "      WHERE\n" +
                    "      AC.ID_HORARIO_ACTIVIDAD = CAL.ID_HORARIO_ACTIVIDAD\n" +
                    "      AND CAL.ID_ALUMNO_P = AL.ID_ALUMNOP\n" +
                    "      AND AC.ID_ACTIVIDAD = AT.ID_ACTIVIDAD\n" +
                    "      AND ALM.MATRICULA = AL.MATRICULA\n" +
                    "AND ALM.MATRICULA = ? ORDER BY AL.ID_ALUMNOP";

    String QRY_GET_ALUMNO_BY_MATRICULA =
            "SELECT ID_ALUMNO, MATRICULA, NOMBRES, APE_PATERNO, APE_MATERNO, ESTATUS\n" +
                    "FROM ALUMNOS\n" +
                    "WHERE MATRICULA = ?";
}
