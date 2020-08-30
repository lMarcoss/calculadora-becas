package edu.calc.becas.cache.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.RestTemplateService;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.grupos.model.GrupoDtoSHorario;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.model.LicenciaturaDtoSHorario;
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
import java.util.Arrays;
import java.util.List;

import static edu.calc.becas.cache.DataScheduleSystem.*;

/**
 * Document me
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 30/08/20
 */
@Slf4j
@Service
public class CatalogosSystemaHorariosImpl extends RestTemplateService implements CatalogosSystemaHorarios {

    @Value("${prop.sistema.horarios.api.carreras.vigentes}")
    private String pathCarrerasVigentes;
    @Value("${prop.sistema.horarios.api.carreras.detallecarrera}")
    private String pathDetalleCarrera;

    @Value("${prop.sistema.horarios.api.grupos}")
    private String pathGrupos;

    @Value("${prop.sistema.horarios.api.periodo.actual}")
    private String pathPeriodoActual;

    @Value("${prop.sistema.horarios.api.periodo.lista}")
    private String pathPeriodoLista;

    /**
     * Inicializa rest-template
     *
     * @param restTemplate
     * @param messageApplicationProperty
     */
    public CatalogosSystemaHorariosImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(restTemplate, messageApplicationProperty);
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

    @Override
    public Licenciatura getDetailByClaveFromScheduledSystem(String cveCarrera) throws GenericException {

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


    /**
     * Obtiene todos los grupos de un periodo en el sistema de horarios
     *
     * @param cicloEscolarVo periodo
     * @return grupos
     */
    @Override
    public List<Grupo> getAllAllFromScheduledSystem(CicloEscolarVo cicloEscolarVo) {
        String path = urlSistemaHorarios + pathGrupos + "?periodo=" + cicloEscolarVo.getClave();
        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<GrupoDtoSHorario[]> response = restTemplate.exchange(path, HttpMethod.GET, entity, GrupoDtoSHorario[].class);
        List<GrupoDtoSHorario> grupoDtoSHorarios = Arrays.asList(response.getBody());
        List<Grupo> grupos = convertGruposDtoToGrupoList(grupoDtoSHorarios);
        DataScheduleSystem.C_CONSTANT_DATA.put(C_GRUPOS, grupos);
        return grupos;
    }

    @Override
    public CicloEscolarVo getCicloEscolarActualFromScheduledSystem() throws GenericException {
        try {


            String path = urlSistemaHorarios + pathPeriodoActual;
            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<PeriodoDtoSHorario> response = restTemplate.exchange(path, HttpMethod.GET, entity, PeriodoDtoSHorario.class);

            PeriodoDtoSHorario periodoDtoSHorario = response.getBody();

            CicloEscolarVo cicloEscolarVo = convertPeriodoDtoToCicloEscolar(periodoDtoSHorario);
            DataScheduleSystem.C_CONSTANT_DATA.put(C_CICLO_ESCOLAR, cicloEscolarVo);
            return cicloEscolarVo;


        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerPeriodoActual());
        }
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

    /**
     * Mapero de grupos del sistema de horario en objeto Grupo
     *
     * @param grupoDtoSHorarios
     * @return
     */
    private List<Grupo> convertGruposDtoToGrupoList(List<GrupoDtoSHorario> grupoDtoSHorarios) {
        List<Grupo> grupos = new ArrayList<>();
        grupoDtoSHorarios.forEach(grupoDtoSHorario -> {
            grupos.add(createGrupo(grupoDtoSHorario));
        });
        return grupos;
    }

    /**
     * Mapeo del grupodto en objeto Grupo
     *
     * @param grupoDtoSHorario
     * @return
     */
    private Grupo createGrupo(GrupoDtoSHorario grupoDtoSHorario) {
        Grupo grupo = new Grupo();
        grupo.setCveGrupo(grupoDtoSHorario.getClave());
        grupo.setNombreGrupo(grupoDtoSHorario.getNombre());
        grupo.setCveLicenciatura(grupoDtoSHorario.getCarrera());
        grupo.setCvePeriodo(grupoDtoSHorario.getPeriodo());
        return grupo;
    }
    
    /**
     * Convierte periodoDto a Cicloescolar
     *
     * @param periodoDtoSHorario
     * @return
     */
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
