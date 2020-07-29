package edu.calc.becas.mcatalogos.defpercentbeca.service;

import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;

/**
 * Servicio para obtener base para calcular % de becas
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 03/05/20
 */
public interface DefPorcentajeActividadService {
    /**
     * Recupera todas las definiciones de % de becas
     *
     * @return
     */
    DefPorcentajeActividad getDefPorcentajeActividades();

}
