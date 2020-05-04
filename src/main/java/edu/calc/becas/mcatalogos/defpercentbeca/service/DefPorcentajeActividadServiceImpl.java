package edu.calc.becas.mcatalogos.defpercentbeca.service;

import edu.calc.becas.mcatalogos.defpercentbeca.dao.DefPorcentajeActividadDao;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import org.springframework.stereotype.Service;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 03/05/20
 */
@Service
public class DefPorcentajeActividadServiceImpl implements DefPorcentajeActividadService {
    private DefPorcentajeActividadDao defPorcentajeActividadDao;

    public DefPorcentajeActividadServiceImpl(DefPorcentajeActividadDao defPorcentajeActividadDao) {
        this.defPorcentajeActividadDao = defPorcentajeActividadDao;
    }

    @Override
    public DefPorcentajeActividad getDefPorcentajeActividades() {
        return defPorcentajeActividadDao.getDefPorcentajeActividades();
    }
}
