package edu.calc.becas.malumnos.actividades.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
final class QueriesActividadAlumno {
    static final String QRY_GET_ACTIVIDAD_BY_ALUMNO =
            "SELECT AA.ID_ACTIVIDAD_ALUMNO, A.NOMBRE_ACTIVIDAD\n" +
                    "FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO ADP, ACTIVIDAD_ALUMNO AA, HORARIO_ACTIVIDAD HA, ACTIVIDADES A\n" +
                    "WHERE AL.MATRICULA = ?\n" +
                    "  AND AL.ESTATUS = 'S'\n" +
                    "  AND HA.ESTATUS = 'S'\n" +
                    "  AND A.ESTATUS = 'S'\n" +
                    "  AND A.TIPO_ACTIVIDAD = 'EX'\n" +
                    "  AND AL.MATRICULA = ADP.MATRICULA\n" +
                    "  AND ADP.ID_ALUMNOP = (SELECT MAX(AD.ID_ALUMNOP) FROM ALUMNOS_DAT_PERIODO AD WHERE AD.MATRICULA = ADP.MATRICULA)\n" +
                    "  AND HA.ID_HORARIO_ACTIVIDAD = AA.ID_HORARIO_ACTIVIDAD\n" +
                    "  AND HA.ID_ACTIVIDAD = A.ID_ACTIVIDAD\n" +
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
  static final String QRY_CONDITION_ID_USER =" and AC.ID_USUARIO = ?";

  static final String QRY_ID_ACTIVIDAD_ALUMNO_BY_HORARIO =
          "SELECT AA.ID_ACTIVIDAD_ALUMNO, HA.ID_ACTIVIDAD\n" +
          "FROM HORARIO_ACTIVIDAD HA, ACTIVIDAD_ALUMNO AA\n" +
          "WHERE HA.ID_HORARIO_ACTIVIDAD = ? AND HA.ESTATUS = 'S'\n" +
          "  AND AA.ID_HORARIO_ACTIVIDAD = HA.ID_HORARIO_ACTIVIDAD";
}
