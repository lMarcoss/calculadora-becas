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
public class PorcentajeBecaPorActividad implements Serializable {
    private String cveActividad;// clave actividad
    private String descTipoActvidad;
    private int porcentajeMinimoRequerido; // porcentaje minimo requerido para obtener el porcentaje de actividad cumplida
    private int porcentajeBecaActividadIncumplida; // porcentaje que corresponde si el alumno no cumple el porcentaje minimo
    private int porcentajeBecaActividadCumplida;

    public int calculaPorcentajeBecaPorPorcentajeActividad(int porcentajeActividad) {
        if (porcentajeActividad >= getPorcentajeMinimoRequerido()) {
            return getPorcentajeBecaActividadCumplida();
        } else {
            return getPorcentajeBecaActividadIncumplida();
        }
    }
}
