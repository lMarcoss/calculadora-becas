package edu.calc.becas.mcatalogos.licenciaturas.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;

import java.util.List;

/**
 * Servicios para consulta de licenciaturas
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/23/19
 */
public interface LicenciaturaService {
    /**
     * Consulta todas  las licenciaturas
     *
     * @return licenciaturas
     * @throws GenericException error en la consulta
     */
    WrapperData getAll() throws GenericException;

    /**
     * Obtiene los datos de una licenciatura por clave
     *
     * @param cveCarrera clave licenciatura
     * @return licenciatura
     * @throws GenericException eror en la consulta
     */
    Licenciatura getDetailByClave(String cveCarrera) throws GenericException;

    /**
     * Consulta las licenciaturas del sitema de horarios
     *
     * @return licenciaturas
     */
    List<Licenciatura> getAllFromScheduledSystem();
}

