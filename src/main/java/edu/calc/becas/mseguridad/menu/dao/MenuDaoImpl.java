package edu.calc.becas.mseguridad.menu.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.menu.model.Menu;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.mseguridad.menu.dao.QueriesMenu.QRY_GET_CHLIDS_BY_PARENT;
import static edu.calc.becas.mseguridad.menu.dao.QueriesMenu.QRY_GET_PARENTS_BY_USER;

@Repository
public class MenuDaoImpl extends BaseDao implements MenuDao {

    public MenuDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public List<Menu> getMenu(String username) throws GenericException {
        List<Menu> menu = getParents();
        if (menu == null) {
            throw new GenericException("Menú vacío");
        }
        getChild(menu, username);
        return menu;
    }

    private void getChild(List<Menu> menu, String username) {
        for (Menu menu1 : menu) {
            menu1.setChilds(
                    this.jdbcTemplate.query(QRY_GET_CHLIDS_BY_PARENT, new Object[]{
                              menu1.getIdPadre(),
                              username,
                              menu1.getIdPadre(),
                              username}, ((rs, i) -> mapperMenuChild(rs)))
            );
            ;
        }
    }

    private Menu mapperMenuChild(ResultSet rs) throws SQLException {
        Menu child = new Menu();
        child.setIdMenu(rs.getInt("ID_MENU"));
        child.setIdPadre(rs.getInt("ID_PADRE"));
        child.setNombre(rs.getString("NOMBRE"));
        child.setUrl(rs.getString("URL"));
        return child;
    }

    private List<Menu> getParents() {
        return this.jdbcTemplate.query(QRY_GET_PARENTS_BY_USER, ((rs, i) -> mapperMenuParent(rs)));

    }

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
}
