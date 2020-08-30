package edu.calc.becas.cache.service;

import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;

import java.util.List;

/**
 * servicio para obtener la informacion del sistema de horarios y almacenarlo en local
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 30/08/20
 */
public interface CatalogosSystemaHorarios {

    List<Licenciatura> getAllFromScheduledSystem();

    Licenciatura getDetailByClaveFromScheduledSystem(String cveCarrera) throws GenericException;

    List<Grupo> getAllAllFromScheduledSystem(CicloEscolarVo cicloEscolarVo);

    CicloEscolarVo getCicloEscolarActualFromScheduledSystem() throws GenericException;

}
