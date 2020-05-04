package edu.calc.becas.mreporte.actividades.percent.beca.model;

import edu.calc.becas.common.model.CommonData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
@Getter
@Setter
@NoArgsConstructor
public class ReporteBecaPeriodo extends CommonData {
    private int idPorcentajeBeca;
    private String matricula;
    private String Nombres;
    private String cveGrupo;
    private String descGrupo;
    private String cveLicenciatura;
    private String descLicenciatura;
    private String cvePeriodo;
    private String descPeriodo;
    private int idActividad;
    private String descActividad;
    private int porcentajeBecaTallerCLubP1;//porcentaje de beca parcial 1
    private int porcentajeBecaTallerCLubP2;//porcentaje de beca parcial 2
    private int porcentajeBecaTallerCLubP3;//porcentaje de beca parcial 3
    private int porcentajeBecaBibliotecaP1;//porcentaje de beca parcial 1
    private int porcentajeBecaBibliotecaP2;//porcentaje de beca parcial 2
    private int porcentajeBecaBibliotecaP3;//porcentaje de beca parcial 3
    private int porcentajeBecaSalaComputoP1;//porcentaje de beca parcial 1
    private int porcentajeBecaSalaComputoP2;//porcentaje de beca parcial 2
    private int porcentajeBecaSalaComputoP3;//porcentaje de beca parcial 3
}
