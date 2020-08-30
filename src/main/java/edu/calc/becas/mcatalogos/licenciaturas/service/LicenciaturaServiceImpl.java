package edu.calc.becas.mcatalogos.licenciaturas.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.cache.service.CatalogosSystemaHorarios;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.RestTemplateService;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private final CatalogosSystemaHorarios catalogosSystemaHorarios;

    public LicenciaturaServiceImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty, CatalogosSystemaHorarios catalogosSystemaHorarios) {
        super(restTemplate, messageApplicationProperty);
        this.catalogosSystemaHorarios = catalogosSystemaHorarios;
    }

    public WrapperData<Licenciatura> getAll() throws GenericException {


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
        return catalogosSystemaHorarios.getAllFromScheduledSystem();
    }

    @Override
    public Licenciatura getDetailByClave(String cveCarrera) throws GenericException {

        boolean found = false;
        Licenciatura licenciatura = null;
        WrapperData<Licenciatura> licenciaturaWrapperData = this.getAll();
        for (Licenciatura licenciaturaL : licenciaturaWrapperData.getData()) {
            if (licenciaturaL.getCveLicenciatura().equalsIgnoreCase(cveCarrera)) {
                found = true;
                licenciatura = licenciaturaL;
            }
        }

        if (found) {
            return licenciatura;
        } else {
            return catalogosSystemaHorarios.getDetailByClaveFromScheduledSystem(cveCarrera);
        }
    }

}
