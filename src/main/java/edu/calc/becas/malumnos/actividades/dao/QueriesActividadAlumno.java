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

    static final String QRY_GET_ALL_ACTIVIDADES_ALUMNOS =
            "SELECT AT.ID_ACTIVIDAD,\n" +
                    "       CAL.ID_ALUMNO_P,\n" +
                    "       AT.NOMBRE_ACTIVIDAD,\n" +
                    "       ALM.MATRICULA,\n" +
                    "       ALM.NOMBRES,\n" +
                    "       ALM.APE_PATERNO,\n" +
                    "       ALM.APE_MATERNO,\n" +
                    "       Concat(AC.HORA, ':', AC.AM_PM) HORARIO,\n" +
                    "       AL.DESC_GRUPO,\n" +
                    "       U.USERNAME USERNAME_ENCARGADO,\n" +
                    "       U.NOMBRES NOMBRE_ENCARGADO,\n" +
                    "       U.APE_PATERNO AP_PATERNO_ENCARGADO,\n" +
                    "       U.APE_MATERNO AP_MATERNO_ENCARGADO\n" +
                    "FROM ALUMNOS ALM,\n" +
                    "     HORARIO_ACTIVIDAD AC,\n" +
                    "     ACTIVIDAD_ALUMNO CAL,\n" +
                    "     ALUMNOS_DAT_PERIODO AL,\n" +
                    "     ACTIVIDADES AT,\n" +
                    "     USUARIOS U\n" +
                    "WHERE AC.ID_HORARIO_ACTIVIDAD = CAL.ID_HORARIO_ACTIVIDAD\n" +
                    "  AND CAL.ID_ALUMNO_P = AL.ID_ALUMNOP\n" +
                    "  AND AC.ID_ACTIVIDAD = AT.ID_ACTIVIDAD AND AC.ID_USUARIO = U.ID_USUARIO AND U.ESTATUS = 'S' AND AT.ESTATUS = 'S'\n" +
                    "  AND ALM.MATRICULA = AL.MATRICULA";


  static final String QRY_CONDITION_ID_ACTIVIDAD =" and AT.ID_ACTIVIDAD = ?\n";
  static final String QRY_CONDITION_USERNAME =" AND U.USERNAME = ?\n";
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
