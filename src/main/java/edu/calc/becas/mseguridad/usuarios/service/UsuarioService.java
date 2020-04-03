package edu.calc.becas.mseguridad.usuarios.service;

import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/14/19
 */
public interface UsuarioService extends CrudGenericService<Usuario> {
    Usuario getByUsername(String username);
}
