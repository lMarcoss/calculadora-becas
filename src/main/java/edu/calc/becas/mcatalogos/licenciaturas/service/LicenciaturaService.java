package edu.calc.becas.mcatalogos.licenciaturas.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Services definition for majors
 * Date: 3/23/19
 */
public interface LicenciaturaService {
    WrapperData getAll() throws GenericException;

    Licenciatura getDetailByClave() throws GenericException;
}

