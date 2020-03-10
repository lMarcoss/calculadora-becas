package edu.calc.becas.common.base.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 2019-06-25
 */
public interface CrudGenericDao<T> {
    WrapperData getAllByStatus(int page, int pageSize, String status);

    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1);

    T add(T object) throws GenericException;

    T update(T object);
}
