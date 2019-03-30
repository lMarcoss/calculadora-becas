package edu.calc.becas.catalogos.licenciaturas.service;

import edu.calc.becas.catalogos.licenciaturas.dao.LicenciaturaDao;
import edu.calc.becas.catalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.common.model.WrapperData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 3/23/19
 */
@Service
public class LicenciaturaServiceImpl implements LicenciaturaService {

    private final LicenciaturaDao licenciaturaDao;

    @Autowired
    public LicenciaturaServiceImpl(LicenciaturaDao licenciaturaDao) {
        this.licenciaturaDao = licenciaturaDao;
    }

    @Override
    public WrapperData getAll(int page, int pageSize) {
        return licenciaturaDao.getAll(page, pageSize);
    }

    @Override
    public Licenciatura add(Licenciatura lic) {
        return licenciaturaDao.add(lic);
    }

    @Override
    public Licenciatura update(Licenciatura lic) {
        return licenciaturaDao.update(lic);
    }
}
