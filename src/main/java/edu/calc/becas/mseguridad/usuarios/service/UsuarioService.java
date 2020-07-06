package edu.calc.becas.mseguridad.usuarios.service;

import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

/**
 * define metodos para administracion de usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
public interface UsuarioService extends CrudGenericService<Usuario> {
    /**
     * metodo para obtener usuario por username
     *
     * @param username username del usuario a recuperar
     * @return usuario
     */
    Usuario getByUsername(String username);
}
