package edu.calc.becas.mcatalogos.licenciaturas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.CommonMethodToRestTemplate;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.model.LicenciaturaDtoSHorario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 3/23/19
 */
@Service
@Slf4j
public class LicenciaturaServiceImpl extends CommonMethodToRestTemplate implements LicenciaturaService {

    @Value("${prop.sistema.horarios.api.carreras.vigentes}")
    private String pathCarrerasVigentes;

    @Value("${prop.sistema.horarios.api.carreras.detallecarrera}")
    private String pathDetalleCarrera;


    public LicenciaturaServiceImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(restTemplate, messageApplicationProperty);
    }

    public WrapperData getAll() throws GenericException {
        // obtiene carreras vigentes del sistema de horarios
        String path = urlSistemaHorarios + pathCarrerasVigentes;

        WrapperData<Licenciatura> wrapperData = new WrapperData<>();


        try {

            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<LicenciaturaDtoSHorario[]> response = restTemplate.exchange(path, HttpMethod.GET, entity, LicenciaturaDtoSHorario[].class);

            List<LicenciaturaDtoSHorario> licenciaturasDto = Arrays.asList(response.getBody());

            List<Licenciatura> licenciaturas = convertListDtoLicenciaturasToLicenciatura(licenciaturasDto);
            wrapperData.setPage(0);
            wrapperData.setPageSize(licenciaturas.size());
            wrapperData.setLengthData(licenciaturas.size());
            wrapperData.setData(licenciaturas);
            return wrapperData;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerLicencuaturasSistemaHorario());
        }

    }

    @Override
    public Licenciatura getDetailByClave() throws GenericException {
        // obtiene detalle de carrera
        String path = urlSistemaHorarios + pathDetalleCarrera;
        try {
            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<LicenciaturaDtoSHorario> response = restTemplate.exchange(path, HttpMethod.GET, entity, LicenciaturaDtoSHorario.class);
            LicenciaturaDtoSHorario licenciaturaDtoSHorario = response.getBody();
            Licenciatura licenciatura = createLicenciatura(licenciaturaDtoSHorario);
            return licenciatura;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerDetalleLicencuaturaSistemaHorario());
        }
    }

    private List<Licenciatura> convertListDtoLicenciaturasToLicenciatura(List<LicenciaturaDtoSHorario> licenciaturasDto) {
        List<Licenciatura> licenciaturas = new ArrayList<>();
        licenciaturasDto.forEach(licenciaturaDto -> {
            Licenciatura lic = createLicenciatura(licenciaturaDto);
            licenciaturas.add(lic);
        });
        return licenciaturas;
    }

    private Licenciatura createLicenciatura(LicenciaturaDtoSHorario licenciaturaDto) {
        Licenciatura lic = new Licenciatura();
        lic.setCveLicenciatura(licenciaturaDto.getClave());
        lic.setNombreLicenciatura(licenciaturaDto.getNombre());
        if (licenciaturaDto.getVigente() != null) {
            lic.setVigente(licenciaturaDto.getVigente());
        }
        return lic;
    }

}
