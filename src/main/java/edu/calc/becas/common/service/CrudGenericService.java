package edu.calc.becas.common.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;

/**
 * Define servicios genericos para administracion de todos los objetos de la aplicacion
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-25
 */
public interface CrudGenericService<T> {
    /**
     * Obtener los registros por estatus
     *
     * @param page     pagina a recuperar
     * @param pageSize registros a recuperar para una pagina
     * @param status   estatus de los registros a recuperar
     * @return envoltura de objetos T paginado por estatus
     */
    WrapperData getAllByStatus(int page, int pageSize, String status);

    /**
     * Obtener todos los registros por status y por param1
     *
     * @param page     pagina a recuperar
     * @param pageSize registros a recuperar para una pagina
     * @param status   estatus de los registros a recuperar
     * @param param1   dato extra a considerar al obtener los registros
     * @return envoultura de objetos T paginado, por estatus y por param1
     */
    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1);

    /**
     * Registra un objeto (registro) T
     *
     * @param t objeto a registrar
     * @return objeto registrado
     * @throws GenericException
     */
    T add(T t) throws GenericException;

    /**
     * actualiza datos de un objeto (registro) T
     *
     * @param t registro a actualizar
     * @return registro actualizado
     */
    T update(T t);
}
