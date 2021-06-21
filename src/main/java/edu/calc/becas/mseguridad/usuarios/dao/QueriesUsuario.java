package edu.calc.becas.mseguridad.usuarios.dao;

/**
 * Se definen las consultas para obtener usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
final class QueriesUsuario {
    private QueriesUsuario() {
    }

    static final String QRY_GET_ALL = "SELECT U.ID_USUARIO, U.NOMBRES, U.APE_PATERNO, U.APE_MATERNO,  U.USERNAME, U.ESTATUS, R.NOMBRE_ROL TIPO_USUARIO,R.ID_ROL, U.D_TOLERANCIA_REP, U.FECHA_REG_TOLERANCIA\n" +
            "FROM USUARIOS U, ROLES R WHERE U.ID_ROL = R.ID_ROL ";
    static final String QRY_CONDITION_ESTATUS = "\nAND U.ESTATUS = ? ";
    static final String QRY_CONDITION_TIPO_USUARIO = "\nAND U.ID_ROL = ?";
    static final String QRY_CONDITION_BY_USERNAME = "\nAND U.USERNAME = ?";
    static final String QRY_ADD =
            "INSERT INTO USUARIOS\n" +
                    "(NOMBRES, APE_PATERNO, APE_MATERNO, ID_ROL, USERNAME, PASSWORD, D_TOLERANCIA_REP, ESTATUS, AGREGADO_POR, FECHA_CREACION)\n" +
                    "  VALUE (?, ?, ?, ?, ?, sha2(concat(?, ?, ?), 224), ?, ?,?, NOW())";

    static final String QRY_UPDATE =
            "UPDATE USUARIOS\n" +
                    "SET NOMBRES = ?, APE_PATERNO = ?, APE_MATERNO = ?, ID_ROL = ?, USERNAME = ?,\n" +
                    " D_TOLERANCIA_REP= ?, ESTATUS   = ?, ACTUALIZADO_POR = ?, FECHA_ACTUALIZACION = NOW() \n" +
                    "WHERE ID_USUARIO = ?";

    static final String QRY_UPDATE_WITH_PASSWORD =
            "UPDATE USUARIOS\n" +
                    "SET NOMBRES = ?, APE_PATERNO = ?, APE_MATERNO = ?, ID_ROL = ?, USERNAME = ?, PASSWORD = sha2(concat(?, ?, ?), 224),\n" +
                    "  D_TOLERANCIA_REP = ?, ESTATUS   = ?, ACTUALIZADO_POR = ?, FECHA_ACTUALIZACION = NOW()\n" +
                    "WHERE ID_USUARIO = ?";

    static final String QRY_ORDER_BY = "\nORDER BY NOMBRES ASC, APE_PATERNO ASC, APE_MATERNO ASC";
}
