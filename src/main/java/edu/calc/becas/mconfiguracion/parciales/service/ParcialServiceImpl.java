package edu.calc.becas.mconfiguracion.parciales.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.parciales.dao.ParcialDao;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Implementación de servicios para la tabla PARCIAL
 * Date: 4/9/19
 */
@Service
public class ParcialServiceImpl implements ParcialService {

    private final ParcialDao parcialDao;

    @Autowired
    public ParcialServiceImpl(ParcialDao parcialDao) {
        this.parcialDao = parcialDao;
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
    public Parcial getParcialActual() {
        return parcialDao.getParcialActual();
    }
}
