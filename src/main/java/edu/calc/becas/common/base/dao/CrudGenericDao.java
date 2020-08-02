package edu.calc.becas.common.base.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;

/**
 * Define metodos comunes para operaciones con la BD
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-25
 */
public interface CrudGenericDao<T> {
    /**
     * Obtiene todos los registros por estatus
     *
     * @param page     pagina
     * @param pageSize registros por pagina
     * @param status   estatus a recuperar
     * @return registros
     */
    WrapperData getAllByStatus(int page, int pageSize, String status);

    /**
     * Obtiene todos los registros por estatus y un parametro extra
     *
     * @param page     pagina
     * @param pageSize registros por pagina
     * @param status   estatus a recuperar
     * @param param1   parametro extra
     * @return registros que cumplen las condiciones status y param1
     */
    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1);

    /**
     * Guarda  el registro
     *
     * @param object registros
     * @return registro guardado
     * @throws GenericException error al guardar
     */
    T add(T object) throws GenericException;

    /**
     * Actualiza el registro
     *
     * @param object registro
     * @return registro actualizado
     */
    T update(T object);
}
