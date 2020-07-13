package edu.calc.becas.mseguridad.rolesypermisos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mseguridad.rolesypermisos.dao.RolDao;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion de servicios para recuperar roles de usuarios
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Service
public class RolServiceImpl implements RolService {

    private final RolDao rolDao;

    @Autowired
    public RolServiceImpl(RolDao rolDao) {
        this.rolDao = rolDao;
    }

    /**
     * Recupera roles por paginacion y estatus
     *
     * @param page     pagina a recuperar
     * @param pageSize registros a recuperar para una pagina
     * @param status   estatus de los registros a recuperar
     * @return
     */
    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return rolDao.getAllByStatus(page, pageSize, status);
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String tipoUsuario) {
        return null;
    }

    @Override
    public Rol add(Rol rol) {
        return null;
    }

    @Override
    public Rol update(Rol rol) {
        return null;
    }
}
