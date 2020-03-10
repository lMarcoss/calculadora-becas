package edu.calc.becas.mcatalogos.grupos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 3/26/19
 */
public interface GrupoService {
    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException;
}
