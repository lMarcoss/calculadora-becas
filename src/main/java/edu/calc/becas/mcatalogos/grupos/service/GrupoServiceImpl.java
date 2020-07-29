package edu.calc.becas.mcatalogos.grupos.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.RestTemplateService;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.grupos.model.GrupoDtoSHorario;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.service.LicenciaturaService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
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

import static edu.calc.becas.cache.DataScheduleSystem.C_GRUPOS;
import static edu.calc.becas.common.utils.Constant.LICENCIATURA_DEFAULT;

/**
 * Implementacion de servicios para consulta de grupos
 * <p>
 * se consulta en el sistema de horarios solo si no se tiene en memoria
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
@Service
@Slf4j
public class GrupoServiceImpl extends RestTemplateService implements GrupoService {

    @Value("${prop.sistema.horarios.api.grupos}")
    private String pathGrupos;

    private final LicenciaturaService licenciaturaService;
    private final CicloEscolarService cicloEscolarService;

    public GrupoServiceImpl(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty,
                            LicenciaturaService licenciaturaService, CicloEscolarService cicloEscolarService) {
        super(restTemplate, messageApplicationProperty);
        this.licenciaturaService = licenciaturaService;
        this.cicloEscolarService = cicloEscolarService;
    }

    /**
     * Recuperar todos los grupos por licenciatura
     *
     * @param page         pagina
     * @param pageSize     registros por pagina
     * @param status       estatus
     * @param licenciatura licenciatura
     * @return
     * @throws GenericException
     */
    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();

        try {


            WrapperData<Grupo> wrapperData = new WrapperData<>();

            List<Grupo> grupos = getGrupos(cicloEscolarVo);


            if (!licenciatura.equalsIgnoreCase(LICENCIATURA_DEFAULT)) {
                grupos = filterGroupsByLicenciatura(grupos, licenciatura);
            }
            wrapperData.setData(grupos);

            List<Licenciatura> licenciaturas = licenciaturaService.getAll().getData();

            if (!wrapperData.getData().isEmpty()) {
                // add name career
                addNameCareerByGroup(grupos, licenciaturas);
            }
            wrapperData.setPage(0);
            wrapperData.setPageSize(grupos.size());
            wrapperData.setLengthData(grupos.size());

            return wrapperData;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e, messageApplicationProperty.getErrorObtenerGrupos());
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

    /**
     * obtiene los grupos de un periodo
     *
     * @param cicloEscolarVo
     * @return
     */
    private List<Grupo> getGrupos(CicloEscolarVo cicloEscolarVo) {
        if (DataScheduleSystem.C_CONSTANT_DATA.get(C_GRUPOS) != null) {
            return (List<Grupo>) DataScheduleSystem.C_CONSTANT_DATA.get(C_GRUPOS);
        }

        return getAllAllFromScheduledSystem(cicloEscolarVo);
    }

    /**
     * Se agrega descripcion de la licenciatura por grupo
     *
     * @param grupos
     * @param licenciaturas
     */
    private void addNameCareerByGroup(List<Grupo> grupos, List<Licenciatura> licenciaturas) {
        for (Grupo grupo : grupos) {
            grupo.setNombreLicenciatura(findNameCarrer(licenciaturas, grupo));
        }
    }

    /**
     * filtra nombre de una carrera de un grupo
     *
     * @param licenciaturas
     * @param grupo
     * @return
     */
    private String findNameCarrer(List<Licenciatura> licenciaturas, Grupo grupo) {
        Licenciatura lic = licenciaturas.stream().filter(licenciatura -> licenciatura.getCveLicenciatura().equalsIgnoreCase(grupo.getCveLicenciatura())).findAny().orElse(null);
        if (lic != null) {
            return lic.getNombreLicenciatura();
        } else {
            return null;
        }
    }

    /**
     * Filtra grupos de una licenciatura
     *
     * @param grupos
     * @param licenciatura
     * @return
     */
    private List<Grupo> filterGroupsByLicenciatura(List<Grupo> grupos, String licenciatura) {
        List<Grupo> list = new ArrayList<>();
        grupos.forEach(grupo -> {
            if (grupo.getCveLicenciatura().equalsIgnoreCase(licenciatura)) {
                list.add(grupo);
            }
        });
        return list;
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
}
