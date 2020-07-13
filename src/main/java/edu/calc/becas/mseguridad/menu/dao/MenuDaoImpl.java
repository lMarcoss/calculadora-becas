package edu.calc.becas.mseguridad.menu.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static edu.calc.becas.mseguridad.menu.dao.QueriesMenu.QRY_GET_CHLIDS_BY_PARENT;
import static edu.calc.becas.mseguridad.menu.dao.QueriesMenu.QRY_GET_PARENTS_BY_USER;

/**
 * Repositorio de administracion de menus por usuario con la BD
 */
@Repository
public class MenuDaoImpl extends BaseDao implements MenuDao {

    public MenuDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }


    /**
     * Consulta de menu por usuario
     *
     * @param username username del usuario
     * @return menu del usuario
     * @throws GenericException
     */
    @Override
    public List<Menu> getMenu(String username) throws GenericException {
        List<Menu> parents = getParents();
        if (parents == null) {
            throw new GenericException("Men\u00fa vac\u00edo");
        }
        getChild(parents, username);
        parents = parents.stream().filter(parent -> parent.getChilds().size() > 0).collect(Collectors.toList());
        return parents;
    }

    /**
     * Obtienes los menus principales
     *
     * @return
     */
    private List<Menu> getParents() {
        return this.jdbcTemplate.query(QRY_GET_PARENTS_BY_USER, ((rs, i) -> mapperMenuParent(rs)));

    }

    /**
     * Obtienes los submenus del usuario por menu principal
     *
     * @param parents  menu principal
     * @param username usuario
     */
    private void getChild(List<Menu> parents, String username) {
        for (Menu parent : parents) {
            try {
                parent.setChilds(
                        this.jdbcTemplate.query(QRY_GET_CHLIDS_BY_PARENT, new Object[]{
                                parent.getIdMenu(),
                                username,
                                parent.getIdMenu(),
                                username}, ((rs, i) -> mapperMenuChild(rs))));
            } catch (Exception e) {
                parent.setChilds(new ArrayList<>());
            }

        }
    }

    /**
     * Mapea los menus
     *
     * @param rs resultado de la consulta
     * @return menu principal
     * @throws SQLException
     */
    private Menu mapperMenuParent(ResultSet rs) throws SQLException {
        Menu menu = new Menu();
        menu.setCollapsed(false);
        menu.setIdMenu(rs.getInt("ID_MENU"));
        menu.setIdPadre(rs.getInt("ID_PADRE"));
        menu.setNombre(rs.getString("NOMBRE"));
        menu.setUrl(rs.getString("URL"));
        menu.setIcon(rs.getString("ICON"));
        return menu;
    }

    /**
     * Mapea los submenus del usuario
     *
     * @param rs resultado de la consulta
     * @return submenu
     * @throws SQLException
     */
    private Menu mapperMenuChild(ResultSet rs) throws SQLException {
        Menu child = new Menu();
        child.setIdMenu(rs.getInt("ID_MENU"));
        child.setIdPadre(rs.getInt("ID_PADRE"));
        child.setNombre(rs.getString("NOMBRE"));
        child.setUrl(rs.getString("URL"));
        return child;
    }


}
