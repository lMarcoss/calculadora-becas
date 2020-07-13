package edu.calc.becas.mseguridad.menu.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;

import java.util.List;

/**
 * Define los servicios para consulta de menu por usuario
 */
public interface MenuService {
    /**
     * Obtiene el menu por usuario
     *
     * @param username usuario
     * @return menu del usuario
     * @throws GenericException
     */
    List<Menu> getMenu(String username) throws GenericException;
}
