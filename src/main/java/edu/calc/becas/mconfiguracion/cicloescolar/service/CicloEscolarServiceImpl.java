package edu.calc.becas.mconfiguracion.cicloescolar.service;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.CommonMethodToRestTemplate;
import edu.calc.becas.mconfiguracion.cicloescolar.dao.CicloEscolarDao;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.PeriodoDtoSHorario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CicloEscolarServiceImpl extends CommonMethodToRestTemplate implements CicloEscolarService {


    @Value("${prop.sistema.horarios.api.periodo.actual}")
    private String pathPeriodoActual;

    @Value("${prop.sistema.horarios.api.periodo.lista}")
    private String pathPeriodoLista;

    private final CicloEscolarDao cicloEscolarDao;

    public CicloEscolarServiceImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty,
                                   CicloEscolarDao cicloEscolarDao) {
        super(restTemplate, messageApplicationProperty);
        this.cicloEscolarDao = cicloEscolarDao;
    }

    @Override
    public CicloEscolarVo getCicloEscolarActual() throws GenericException {

        String path = urlSistemaHorarios + pathPeriodoActual;
        try {
            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<PeriodoDtoSHorario> response = restTemplate.exchange(path, HttpMethod.GET, entity, PeriodoDtoSHorario.class);

            PeriodoDtoSHorario periodoDtoSHorario = response.getBody();

            return convertPeriodoDtoToCicloEscolar(periodoDtoSHorario);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerPeriodoActual());
        }

    }

    @Override
    public WrapperData getAll() throws GenericException {
        return cicloEscolarDao.getAll();
        /*String path = urlSistemaHorarios + pathPeriodoLista;
        try {
            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<PeriodoDtoSHorario[]> response = restTemplate.exchange(path, HttpMethod.GET, entity, PeriodoDtoSHorario[].class);

            List<PeriodoDtoSHorario> periodosDto = Arrays.asList(response.getBody());


            WrapperData<CicloEscolarVo> wrapperData = new WrapperData<>();
            List<CicloEscolarVo> ciclos = convertPeriodosDtoToCicloEscolarList(periodosDto);
            wrapperData.setPage(0);
            wrapperData.setPageSize(ciclos.size());
            wrapperData.setLengthData(ciclos.size());
            wrapperData.setData(ciclos);
            return wrapperData;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerPeriodoActual());
        }*/
    }

    private List<CicloEscolarVo> convertPeriodosDtoToCicloEscolarList(List<PeriodoDtoSHorario> periodosDto) {
        List<CicloEscolarVo> list = new ArrayList<>();
        periodosDto.forEach(periodoDtoSHorario -> {
            list.add(convertPeriodoDtoToCicloEscolar(periodoDtoSHorario));
        });
        return list;
    }

    private CicloEscolarVo convertPeriodoDtoToCicloEscolar(PeriodoDtoSHorario periodoDtoSHorario) {
        CicloEscolarVo cicloEscolarVo = new CicloEscolarVo();
        cicloEscolarVo.setClave(periodoDtoSHorario.getClave());
        cicloEscolarVo.setNombre(periodoDtoSHorario.getNombre());
        cicloEscolarVo.setTipo(periodoDtoSHorario.getTipo());
        cicloEscolarVo.setFechaInicio(periodoDtoSHorario.getFinicio());
        cicloEscolarVo.setFechaFin(periodoDtoSHorario.getFfin());
        return cicloEscolarVo;
    }

}
