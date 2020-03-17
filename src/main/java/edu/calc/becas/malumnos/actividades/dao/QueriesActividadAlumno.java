package edu.calc.becas.malumnos.actividades.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
final class QueriesActividadAlumno {
    static final String QRY_GET_ACTIVIDAD_BY_ALUMNO =
            "SELECT AA.ID_ACTIVIDAD_ALUMNO\n" +
                    "FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO ADP, ACTIVIDAD_ALUMNO AA\n" +
                    "WHERE AL.MATRICULA = ?\n" +
                    "  AND AL.ESTATUS = 'S'\n" +
                    "  AND AL.MATRICULA = ADP.MATRICULA\n" +
                    "  AND ADP.ID_ALUMNOP = AA.ID_ALUMNO_P\n" +
                    "  AND ADP.CVE_PERIODO = ?";

    static final String QRY_GET_ALL_ACTIVIDADES_ALUMNOS = "SELECT\n" +
      "       AT.ID_ACTIVIDAD,\n" +
      "       CAL.ID_ALUMNO_P,\n" +
      "       AT.NOMBRE_ACTIVIDAD,\n" +
      "       ALM.MATRICULA,\n" +
      "       ALM.NOMBRES,\n" +
      "       ALM.APE_PATERNO,\n" +
      "       ALM.APE_MATERNO,\n" +
      "       Concat ( AC.HORA,':',AC.AM_PM) HORARIO, " +
      "       AL.DESC_GRUPO " +
      "FROM ALUMNOS ALM, HORARIO_ACTIVIDAD AC, ACTIVIDAD_ALUMNO CAL, ALUMNOS_DAT_PERIODO AL, ACTIVIDADES AT\n" +
      "WHERE\n" +
      "AC.ID_HORARIO_ACTIVIDAD = CAL.ID_HORARIO_ACTIVIDAD\n" +
      "AND CAL.ID_ALUMNO_P = AL.ID_ALUMNOP\n" +
      "AND AC.ID_ACTIVIDAD = AT.ID_ACTIVIDAD\n" +
      "AND ALM.MATRICULA = AL.MATRICULA";


  static final String QRY_CONDITION_ID_ACTIVIDAD =" and AT.ID_ACTIVIDAD = ?";
}
