package edu.calc.becas.mseguridad.usuarios.dao;

import edu.calc.becas.common.base.dao.CrudGenericDao;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

/**
 * Define metodos para consulta de usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
public interface UsuarioDao extends CrudGenericDao<Usuario> {
    /**
     * Obtener usuario por username
     *
     * @param username usuario a recuperar
     * @return usuario
     */
    Usuario getByUsername(String username);
}
