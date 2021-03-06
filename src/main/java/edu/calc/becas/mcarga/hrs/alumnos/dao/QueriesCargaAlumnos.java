package edu.calc.becas.mcarga.hrs.alumnos.dao;

public class QueriesCargaAlumnos {
  static final String QRY_ID_ALUMNO = "SELECT max(ID_ALUMNO)+1 FROM ALUMNOS";
  static final String QRY_ADD = "INSERT INTO ALUMNOS (CURP, MATRICULA, NOMBRES, APE_PATERNO, APE_MATERNO, ESTATUS, FECHA_CREACION, AGREGADO_POR, CODIGO_RFID) VALUES\n" +
    "(?,?,?,?,?,?,CURDATE(),?, ?)";

  static final String QRY_EXISTE_ALUMNO =" SELECT COUNT(1) FROM ALUMNOS WHERE MATRICULA = ? ";

  static final String QRY_ADD_ALUMNO_PERIODO = "INSERT INTO ALUMNOS_DAT_PERIODO (MATRICULA,CVE_GRUPO, DESC_GRUPO, CVE_LICENCIATURA, DESC_LICENCIATURA, CVE_PERIODO, DESC_PERIDODO) VALUES\n" +
    "(?,?,?,?,?,?,?);";

  static final String QRY_EXISTE_ALUMNO_PERIODO ="SELECT COUNT(1) FROM ALUMNOS_DAT_PERIODO WHERE MATRICULA = ?";
}
