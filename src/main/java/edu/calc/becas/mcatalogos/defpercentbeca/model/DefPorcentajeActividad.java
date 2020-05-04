package edu.calc.becas.mcatalogos.defpercentbeca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
@Getter
@Setter
@NoArgsConstructor
public class DefPorcentajeActividad implements Serializable {
    private PorcentajeBecaPorActividad porcentajeBecaTaller;
    private PorcentajeBecaPorActividad porcentajeBecaSala;
    private PorcentajeBecaPorActividad porcentajeBecaBiblioteca;
}
