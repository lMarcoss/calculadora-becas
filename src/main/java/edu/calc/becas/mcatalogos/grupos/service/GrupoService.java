package edu.calc.becas.mcatalogos.grupos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

import java.util.List;

/**
 * Servicios para consulta de grupos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
public interface GrupoService {
    WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException;

    List<Grupo> getAllAllFromScheduledSystem(CicloEscolarVo cicloEscolarVo);

}
