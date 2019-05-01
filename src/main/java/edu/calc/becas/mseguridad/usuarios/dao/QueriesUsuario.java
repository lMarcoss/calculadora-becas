package edu.calc.becas.mseguridad.usuarios.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/14/19
 */
final class QueriesUsuario {
    private QueriesUsuario() {
    }

    static final String QRY_COUNT_ITEM = "SELECT COUNT(1) FROM USUARIOS U WHERE 1 = 1 ";
    static final String QRY_GET_ALL = "SELECT * FROM USUARIOS U WHERE 1 = 1";
    static final String QRY_CONDITION_ESTATUS = " AND U.ESTATUS = ? ";
    static final String QRY_ADD =
            "INSERT INTO USUARIOS\n" +
                    "(NOMBRES, APE_PATERNO, APE_MATERNO, TIPO_USUARIO, USERNAME, PASSWORD, ESTATUS, AGREGADO_POR, FECHA_CREACION)\n" +
                    "  VALUE (?, ?, ?, ?, ?, sha2(concat(?, ?, ?), 224), ?, ?, NOW())";

    static final String QRY_UPDATE =
            "UPDATE USUARIOS\n" +
                    "SET NOMBRES = ?, APE_PATERNO = ?, APE_MATERNO = ?, TIPO_USUARIO = ?, USERNAME = ?,\n" +
                    "  ESTATUS   = ?, ACTUALIZADO_POR = ?, FECHA_ACTUALIZACION = NOW() \n" +
                    "WHERE ID_USUARIO = ?";

    static final String QRY_UPDATE_WITH_PASSWORD =
            "UPDATE USUARIOS\n" +
                    "SET NOMBRES = ?, APE_PATERNO = ?, APE_MATERNO = ?, TIPO_USUARIO = ?, USERNAME = ?, PASSWORD = sha2(concat(?, ?, ?), 224),\n" +
                    "  ESTATUS   = ?, ACTUALIZADO_POR = ?, FECHA_ACTUALIZACION = NOW()\n" +
                    "WHERE ID_USUARIO = ?";
}
