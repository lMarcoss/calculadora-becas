package edu.calc.becas.mconfiguracion.parciales.dao;

import edu.calc.becas.common.base.dao.CrudGenericDao;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;

import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
public interface ParcialDao extends CrudGenericDao<Parcial> {
    List<Parcial> getAllByPeriodo(String cvePeriodo);

    Parcial getParcialActual(CicloEscolarVo cicloEscolarVo) throws GenericException;

    Parcial getParcialAnterior(Parcial parcialActual);
}
