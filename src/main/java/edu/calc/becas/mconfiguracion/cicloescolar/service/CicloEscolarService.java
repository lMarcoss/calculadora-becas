package edu.calc.becas.mconfiguracion.cicloescolar.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

public interface CicloEscolarService {

    CicloEscolarVo getCicloEscolarActual() throws GenericException;


    WrapperData<CicloEscolarVo> getAll() throws GenericException;
}
