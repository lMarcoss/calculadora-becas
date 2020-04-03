package edu.calc.becas.mseguridad.usuarios.dao;

import edu.calc.becas.common.base.dao.CrudGenericDao;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/14/19
 */
public interface UsuarioDao extends CrudGenericDao<Usuario> {
    Usuario getByUsername(String username);
}
