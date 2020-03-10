package edu.calc.becas.common.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-25
 */
public interface CrudGenericService<T> {
    WrapperData getAllByStatus(int page, int pageSize, String status);

    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1);


    T add(T t) throws GenericException;

    T update(T t);
}
