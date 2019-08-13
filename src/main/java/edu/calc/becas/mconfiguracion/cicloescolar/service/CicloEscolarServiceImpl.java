package edu.calc.becas.mconfiguracion.cicloescolar.service;

import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CicloEscolarServiceImpl implements CicloEscolarService {


    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        WrapperData<CicloEscolarVo> wrapperData = new WrapperData<>();

        List<CicloEscolarVo> ciclos = new ArrayList<>();

        ciclos.add(createCicloEscolar("1819B", "SEM-MAR/19-JUN/19", "B", "2019-03-04", "2019-07-28"));

        wrapperData.setData(ciclos);
        wrapperData.setPage(0);
        wrapperData.setPageSize(ciclos.size());
        wrapperData.setLengthData(ciclos.size());
        return wrapperData;
    }

    private CicloEscolarVo createCicloEscolar(String clave, String nombre, String tipo, String fechaInicio, String fechaFin) {
        CicloEscolarVo ciclo = new CicloEscolarVo();
        ciclo.setClave(clave);
        ciclo.setNombre(nombre);
        ciclo.setTipo(tipo);
        ciclo.setFechaInicio(fechaInicio);
        ciclo.setFechaFin(fechaFin);
        return ciclo;
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return null;
    }

    @Override
    public CicloEscolarVo add(CicloEscolarVo ciclo) {
        return null;
    }

    @Override
    public CicloEscolarVo update(CicloEscolarVo cicloEscolarVo) {
        return null;
    }

    @Override
    public List<LabelValueData> getListCatalog() {
        return null;
    }


}
