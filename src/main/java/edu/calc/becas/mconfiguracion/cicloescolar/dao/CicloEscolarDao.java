package edu.calc.becas.mconfiguracion.cicloescolar.dao;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 10/03/20
 */
public interface CicloEscolarDao {
    WrapperData<CicloEscolarVo> getAll();
}
