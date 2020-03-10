package edu.calc.becas.mseguridad.menu.dao;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> getMenu(String username) throws GenericException;
}
