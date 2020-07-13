package edu.calc.becas.mseguridad.menu.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;

import java.util.List;

/**
 * Define los servicios para consulta de menu por usuario
 */
public interface MenuDao {
    /**
     * Consulta de menu por username
     *
     * @param username username del usuario
     * @return menu del usuario
     * @throws GenericException
     */
    List<Menu> getMenu(String username) throws GenericException;
}
