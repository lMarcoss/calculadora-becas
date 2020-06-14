package edu.calc.becas.malumnos.actividades.dao;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-16
 */
final class QueriesActividadAlumno {
    static final String QRY_GET_ACTIVIDAD_BY_ALUMNO =
            "SELECT AA.ID_ACTIVIDAD_ALUMNO, A.NOMBRE_ACTIVIDAD, A.ID_ACTIVIDAD\n" +
                    "FROM ALUMNOS AL,\n" +
                    "     ALUMNOS_DAT_PERIODO ADP,\n" +
                    "     ACTIVIDAD_ALUMNO AA,\n" +
                    "     HORARIO_ACTIVIDAD HA,\n" +
                    "     ACTIVIDADES A\n" +
                    "WHERE AL.MATRICULA = ?\n" +
                    "  AND AL.MATRICULA = ADP.MATRICULA\n" +
                    "  AND ADP.ESTATUS = 'S'\n" +
                    "  AND AL.ESTATUS = 'S'\n" +
                    "  AND ADP.ID_ALUMNOP = AA.ID_ALUMNO_P\n" +
                    "  AND AA.ID_HORARIO_ACTIVIDAD = HA.ID_HORARIO_ACTIVIDAD\n" +
                    "  AND HA.ESTATUS = 'S'\n" +
                    "  AND HA.CVE_PERIODO = ADP.CVE_PERIODO\n" +
                    "  AND HA.ID_ACTIVIDAD = A.ID_ACTIVIDAD\n" +
                    "  AND A.TIPO_ACTIVIDAD = 'EX'\n" +
                    "  AND A.ESTATUS = 'S'\n" +
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
          "SELECT AA.ID_ACTIVIDAD_ALUMNO, HA.ID_ACTIVIDAD, ADP.MATRICULA\n" +
                  "FROM HORARIO_ACTIVIDAD HA,\n" +
                  "     ACTIVIDAD_ALUMNO AA,\n" +
                  "     ALUMNOS_DAT_PERIODO ADP\n" +
                  "WHERE HA.ID_HORARIO_ACTIVIDAD = ?\n" +
                  "  AND HA.ESTATUS = 'S'\n" +
                  "  AND AA.ID_HORARIO_ACTIVIDAD = HA.ID_HORARIO_ACTIVIDAD\n" +
                  "  AND ADP.ID_ALUMNOP = AA.ID_ALUMNO_P\n" +
                  "  AND ADP.ESTATUS = 'S'";
}
