package edu.calc.becas.mseguridad.login.dao;

public class QueriesLogin {

    static final String QRY_GET_INFO_ALUMNO = "SELECT ID_ALUMNO,NOMBRES, APE_PATERNO, APE_MATERNO, '3' ID_ROL, 'ALUMNO' ROL, AC.MATRICULA as USERNAME, CURP COMMONVAL01, AC.CVE_GRUPO COMMONVAL02,\n" +
            "       AC.DESC_LICENCIATURA COMMONVAL03 FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO AC\n" +
            "    WHERE AL.MATRICULA = AC.MATRICULA AND AC.MATRICULA = ? and ESTATUS = 'S' ORDER BY AC.ID_ALUMNOP DESC LIMIT 1";

    static final String QRY_VALIDA_USUARIO = "SELECT DISTINCT U.ID_USUARIO, R.NOMBRE_ROL, U.NOMBRES, U.APE_PATERNO, U.APE_MATERNO, U.DIAS_RETROCESO_REPORTE \n" +
            "FROM USUARIOS U, ROLES R\n" +
            "where U.USERNAME = ?\n" +
            "  AND U.PASSWORD = sha2(concat(?, ?, ?), 224)\n" +
            "  AND U.ID_ROL = R.ID_ROL\n" +
            "  AND R.ESTATUS = 'S'\n" +
            "  AND U.ESTATUS = 'S'";

    static final String QRY_VALIDA_ALUMNO = "SELECT COUNT(1)\n" +
            "FROM ALUMNOS U\n" +
            "where U.MATRICULA = ?\n" +
            "  AND U.ESTATUS = 'S'";
}
