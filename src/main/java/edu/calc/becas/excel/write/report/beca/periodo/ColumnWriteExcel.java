package edu.calc.becas.excel.write.report.beca.periodo;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: lista de columnas a pintar en excel (se usa el ordinal de cada columna para su posicion en el renglon)
 * Date: 07/05/20
 */
public enum ColumnWriteExcel {
    ENUMERATION_INDEX("index", false, true, false),
    MATRICULA("matricula", true, false, false),
    NOMBRES("nombres", true, false, false),
    LICENCIATURA("cveLicenciatura", true, false, false),
    GRUPO("cveGrupo", true, false, false),

    PRIMER_PARCIAL_TALLER_CLUB("porcentajeBecaTallerCLubP1", false,false, true),
    SEGUNDO_PARCIAL_TALLER_CLUB("porcentajeBecaTallerCLubP2", false,false, true),
    TERCER_PARCIAL_TALLER_CLUB("porcentajeBecaTallerCLubP3", false,false, true),
    PORCENTAJE_PROMEDIO_PARCIAL_TALLER_CLUB("porcentajePromedioTallerClub", false,false, true),
    PORCENTAJE_ASIGNADO_TALLER_CLUB_B("porcentajeLogradoTalleClub", false,false, true),

    PRIMER_PARCIAL_BIBLIOTECA("porcentajeBecaBibliotecaP1", false, false, true),
    SEGUNDO_PARCIAL_BIBLIOTECA("porcentajeBecaBibliotecaP2", false, false, true),
    TERCER_PARCIAL_BIBLIOTECA("porcentajeBecaBibliotecaP3", false, false, true),
    PORCENTAJE_PROMEDIO_BIBLIOTECA("porcentajePromedioBiblioteca", false, false, true),
    PORCENTAJE_ASIGNADO__BIBLIOTECA_B("porcentajeLogradoBilioteca", false, false, true),

    PRIMER_PARCIAL_SALA("porcentajeBecaSalaComputoP1", false, false, true),
    SEGUNDO_PARCIAL_SALA("porcentajeBecaSalaComputoP2", false, false, true),
    TERCER_PARCIAL_SALA("porcentajeBecaSalaComputoP3", false, false, true),
    PORCENTAJE_PROMEDIO_SALA("porcentajePromedioSala", false, false, true),
    PORCENTAJE_ASIGNADO_SALA_B("porcentajeLogradoSala", false, false, true);

    private String namePropertyCol;
    private boolean isString;
    private boolean isNumber;
    private boolean isPercent;

    ColumnWriteExcel(String namePropertyCol, boolean isString, boolean isNumber, boolean isPercent) {
        this.namePropertyCol = namePropertyCol;
        this.isString = isString;
        this.isNumber = isNumber;
        this.isPercent = isPercent;
    }

    public String getNamePropertyCol() {
        return namePropertyCol;
    }

    public boolean isString() {
        return isString;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public boolean isPercent() {
        return isPercent;
    }
}
