package edu.calc.becas.mseguridad.usuarios.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.usuarios.dao.UsuarioDao;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion de servicios para administracion de usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDao usuarioDao;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return null;
    }

    /**
     * Obtener usuarios por estatus y por tipo de usuario
     *
     * @param page        pagina a recuperar
     * @param pageSize    registros a recuperar para una pagina
     * @param status      estatus de los registros a recuperar
     * @param tipoUsuario
     * @return lista de registros de usuario de forma paginada
     */
    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String tipoUsuario) {
        return this.usuarioDao.getAllByStatusAndOneParam(page, pageSize, status, tipoUsuario);
    }

    /**
     * Registra un usuario
     *
     * @param usuario usuario a registrar
     * @return usuario registrado
     * @throws GenericException
     */
    @Override
    public Usuario add(Usuario usuario) throws GenericException {
        return this.usuarioDao.add(usuario);
    }

    /**
     * Actualiza los datos de un usuario
     *
     * @param usuario usuario a actualizar
     * @return usuario actualizado
     */
    @Override
    public Usuario update(Usuario usuario) {
        return this.usuarioDao.update(usuario);
    }

    /**
     * recupera usuario por username
     *
     * @param username username del usuario a recuperar
     * @return usuario recuperado
     */
    @Override
    public Usuario getByUsername(String username) {
        return this.usuarioDao.getByUsername(username);
    }
}
