package edu.calc.becas.mcatalogos.grupos.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;

/**
 * Servicios para consulta de grupos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
public interface GrupoService {
    WrapperData<Grupo> getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException;

    Grupo getGrupoByClave(String cveGrupo) throws GenericException;


}
