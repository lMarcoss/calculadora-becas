package edu.calc.becas.mconfiguracion.cicloescolar.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.cache.service.CatalogosSystemaHorarios;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.dao.CicloEscolarDao;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.cache.DataScheduleSystem.C_CICLO_ESCOLAR;

@Service
@Slf4j
public class CicloEscolarServiceImpl implements CicloEscolarService {


    private final CicloEscolarDao cicloEscolarDao;
    private final CatalogosSystemaHorarios catalogosSystemaHorarios;

    public CicloEscolarServiceImpl(CicloEscolarDao cicloEscolarDao,
                                   CatalogosSystemaHorarios catalogosSystemaHorarios) {
        this.cicloEscolarDao = cicloEscolarDao;
        this.catalogosSystemaHorarios = catalogosSystemaHorarios;
    }

    @Override
    public CicloEscolarVo getCicloEscolarActual() throws GenericException {

        if (DataScheduleSystem.C_CONSTANT_DATA.get(C_CICLO_ESCOLAR) != null) {
            return (CicloEscolarVo) DataScheduleSystem.C_CONSTANT_DATA.get(C_CICLO_ESCOLAR);
        }

        return catalogosSystemaHorarios.getCicloEscolarActualFromScheduledSystem();

    }


    @Override
    public WrapperData<CicloEscolarVo> getAll() throws GenericException {
        WrapperData<CicloEscolarVo> periodos = cicloEscolarDao.getAll();
        if (periodos.getData().isEmpty()) {
            try {
                CicloEscolarVo cicloEscolarVo = getCicloEscolarActual();

                List<CicloEscolarVo> periodoActual = new ArrayList<>();
                periodoActual.add(cicloEscolarVo);
                periodos.setData(periodoActual);
            } catch (Exception e) {
                throw new GenericException("No se encontraron datos de periodos");
            }


        }
        return periodos;
    }


}
