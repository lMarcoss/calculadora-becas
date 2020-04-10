package edu.calc.becas.mconfiguracion.parciales.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.dao.ParcialDao;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import edu.calc.becas.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Implementación de servicios para la tabla PARCIAL
 * Date: 4/9/19
 */
@Service
@Slf4j
public class ParcialServiceImpl implements ParcialService {

    private final ParcialDao parcialDao;
    private final CicloEscolarService cicloEscolarService;
    private final UsuarioService usuarioService;


    @Autowired
    public ParcialServiceImpl(ParcialDao parcialDao, CicloEscolarService cicloEscolarService, UsuarioService usuarioService) {
        this.parcialDao = parcialDao;
        this.cicloEscolarService = cicloEscolarService;
        this.usuarioService = usuarioService;
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return parcialDao.getAllByStatus(page, pageSize, status);
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return null;
    }

    @Override
    public Parcial add(Parcial parcial) throws GenericException {
        return parcialDao.add(parcial);
    }

    @Override
    public Parcial update(Parcial parcial) {
        return this.parcialDao.update(parcial);
    }

    @Override
    public List<Parcial> getAllByPeriodo(String cvePeriodo) {
        return this.parcialDao.getAllByPeriodo(cvePeriodo);
    }

    @Override
    public Parcial getParcialActual() throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        return parcialDao.getParcialActual(cicloEscolarVo);
    }

    @Override
    public Parcial getParcialAnterior(Parcial parcialActual) {
        return parcialDao.getParcialAnterior(parcialActual);
    }

    @Override
    public Parcial getParcialByPeriodoAndParcialOrd(int parcial, CicloEscolarVo cicloEscolarVo) {
        return parcialDao.getParcialByPeriodoAndParcialOrd(parcial, cicloEscolarVo);
    }

    @Override
    public List<Parcial> getParcialesPeriodoActualCargaHorasBiblioteca(UserLogin userLogin) throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        Usuario usuario = usuarioService.getByUsername(userLogin.getUsername());
        try {

            List<Parcial> parcials = parcialDao.getAllByPeriodo(cicloEscolarVo.getClave());
            List<Parcial> parcialesDisponiblesParaCarga = new ArrayList<>();


            for (Parcial parcial : parcials) {
                if (Util.parcialFinalizado(parcial, usuario)) {
                    parcialesDisponiblesParaCarga.add(parcial);
                }
            }

            return parcialesDisponiblesParaCarga;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e);
        }
    }
}
