package edu.calc.becas.mconfiguracion.parciales.service;

import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;

import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
public interface ParcialService extends CrudGenericService<Parcial> {

    List<Parcial> getAllByPeriodo(String cvePeriodo);

    Parcial getParcialActual();
}
