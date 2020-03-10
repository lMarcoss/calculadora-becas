package edu.calc.becas.mseguridad.menu.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenu(String username) throws GenericException;
}
