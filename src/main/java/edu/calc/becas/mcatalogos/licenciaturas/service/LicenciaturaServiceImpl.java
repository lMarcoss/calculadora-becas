package edu.calc.becas.mcatalogos.licenciaturas.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.RestTemplateService;
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

import static edu.calc.becas.cache.DataScheduleSystem.C_LICENCIATURA;

/**
 * Implementacion para consulta de licenciaturas en el sistema de horario,
 * <p>
 * se consulta en el sistema de horario solo si no se tiene en memoria
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/23/19
 */
@Service
@Slf4j
public class LicenciaturaServiceImpl extends RestTemplateService implements LicenciaturaService {

    @Value("${prop.sistema.horarios.api.carreras.vigentes}")
    private String pathCarrerasVigentes;

    @Value("${prop.sistema.horarios.api.carreras.detallecarrera}")
    private String pathDetalleCarrera;


    public LicenciaturaServiceImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(restTemplate, messageApplicationProperty);
    }

    public WrapperData getAll() throws GenericException {


        WrapperData<Licenciatura> wrapperData = new WrapperData<>();


        try {
            // obtiene las licenciaturas
            List<Licenciatura> licenciaturas = getLicenciaturasFromScheduledSystem();

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

    private List<Licenciatura> getLicenciaturasFromScheduledSystem() {

        // si ya se tiene localmente se recupera
        if (DataScheduleSystem.C_CONSTANT_DATA.get(C_LICENCIATURA) != null) {
            return (List<Licenciatura>) DataScheduleSystem.C_CONSTANT_DATA.get(C_LICENCIATURA);
        }
        return getAllFromScheduledSystem();
    }

    @Override
    public Licenciatura getDetailByClave(String cveCarrera) throws GenericException {
        // obtiene detalle de carrera
        String path = urlSistemaHorarios + pathDetalleCarrera + "/clave=" + cveCarrera;
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

    @Override
    public List<Licenciatura> getAllFromScheduledSystem() {
        // obtiene carreras vigentes del sistema de horarios
        String path = urlSistemaHorarios + pathCarrerasVigentes;
        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<LicenciaturaDtoSHorario[]> response = restTemplate.exchange(path, HttpMethod.GET, entity, LicenciaturaDtoSHorario[].class);

        List<LicenciaturaDtoSHorario> licenciaturasDto = Arrays.asList(response.getBody());

        List<Licenciatura> licenciaturas = convertListDtoLicenciaturasToLicenciatura(licenciaturasDto);
        DataScheduleSystem.C_CONSTANT_DATA.put(C_LICENCIATURA, licenciaturas);
        return licenciaturas;
    }

    /**
     * Mapeo de licenciaturass
     *
     * @param licenciaturasDto lienciaturas
     * @return licenciaturas mapeados en objeto local
     */
    private List<Licenciatura> convertListDtoLicenciaturasToLicenciatura(List<LicenciaturaDtoSHorario> licenciaturasDto) {
        List<Licenciatura> licenciaturas = new ArrayList<>();
        licenciaturasDto.forEach(licenciaturaDto -> {
            Licenciatura lic = createLicenciatura(licenciaturaDto);
            licenciaturas.add(lic);
        });
        return licenciaturas;
    }

    /**
     * mapeo de datos a objeto local
     */
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
