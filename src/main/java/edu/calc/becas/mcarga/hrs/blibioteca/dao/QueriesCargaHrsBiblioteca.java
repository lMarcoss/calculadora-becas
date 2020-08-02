package edu.calc.becas.mcarga.hrs.blibioteca.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
final class QueriesCargaHrsBiblioteca {

    static final String QRY_INSERT_PERCENT_BIBLIOTECA =
            "INSERT INTO PORCENTAJE_ACTIVIDADES (ID_ACTIVIDAD_ALUMNO, PORCENTAJE_BIBLIOTECA, ID_PARCIAL, CVE_PERIODO,DESC_PERIDODO, AGREGADO_POR, FECHA_CREACION) " +
                    "VALUE (?, ?, ?, ?, ?, ?, NOW())";
    static final String QRY_UPDATE_PERCENT_BIBLIOTECA =
            "UPDATE PORCENTAJE_ACTIVIDADES SET PORCENTAJE_BIBLIOTECA = ? WHERE ID_ACTIVIDAD_ALUMNO = ? AND ID_PARCIAL = ?";

}
