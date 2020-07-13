package edu.calc.becas.mseguridad.menu.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.dao.MenuDao;
import edu.calc.becas.mseguridad.menu.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion de servicios para obtener menus por usuario
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuDao menuDao;

    public MenuServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    /**
     * Recupera el menu por usuario
     *
     * @param username usuario
     * @return menu por usuario
     * @throws GenericException
     */
    @Override
    public List<Menu> getMenu(String username) throws GenericException {
        return menuDao.getMenu(username);
    }
}
