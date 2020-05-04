package edu.calc.becas.mcatalogos.grupos.service;

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

import static edu.calc.becas.common.utils.Constant.LICENCIATURA_DEFAULT;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
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

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException {
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        String path = urlSistemaHorarios + pathGrupos + "?periodo=" + cicloEscolarVo.getClave();
        try {
            HttpEntity entity = new HttpEntity(headers);
            HttpEntity<GrupoDtoSHorario[]> response = restTemplate.exchange(path, HttpMethod.GET, entity, GrupoDtoSHorario[].class);

            List<GrupoDtoSHorario> grupoDtoSHorarios = Arrays.asList(response.getBody());


            WrapperData<Grupo> wrapperData = new WrapperData<>();
            List<Grupo> grupos = convertGruposDtoToGrupoList(grupoDtoSHorarios);


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

    private void addNameCareerByGroup(List<Grupo> grupos, List<Licenciatura> licenciaturas) {
        for (Grupo grupo : grupos) {
            grupo.setNombreLicenciatura(findNameCarrer(licenciaturas, grupo));
        }
    }

    private String findNameCarrer(List<Licenciatura> licenciaturas, Grupo grupo) {
        Licenciatura lic = licenciaturas.stream().filter(licenciatura -> licenciatura.getCveLicenciatura().equalsIgnoreCase(grupo.getCveLicenciatura())).findAny().orElse(null);
        if (lic != null) {
            return lic.getNombreLicenciatura();
        } else {
            return null;
        }
    }

    private List<Grupo> filterGroupsByLicenciatura(List<Grupo> grupos, String licenciatura) {
        List<Grupo> list = new ArrayList<>();
        grupos.forEach(grupo -> {
            if (grupo.getCveLicenciatura().equalsIgnoreCase(licenciatura)) {
                list.add(grupo);
            }
        });
        return list;
    }

    private List<Grupo> convertGruposDtoToGrupoList(List<GrupoDtoSHorario> grupoDtoSHorarios) {
        List<Grupo> grupos = new ArrayList<>();
        grupoDtoSHorarios.forEach(grupoDtoSHorario -> {
            grupos.add(createGrupo(grupoDtoSHorario));
        });
        return grupos;
    }

    private Grupo createGrupo(GrupoDtoSHorario grupoDtoSHorario) {
        Grupo grupo = new Grupo();
        grupo.setCveGrupo(grupoDtoSHorario.getClave());
        grupo.setNombreGrupo(grupoDtoSHorario.getNombre());
        grupo.setCveLicenciatura(grupoDtoSHorario.getCarrera());
        grupo.setCvePeriodo(grupoDtoSHorario.getPeriodo());
        return grupo;
    }
}
