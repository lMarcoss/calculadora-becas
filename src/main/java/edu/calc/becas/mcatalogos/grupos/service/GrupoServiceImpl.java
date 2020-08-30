package edu.calc.becas.mcatalogos.grupos.service;

import edu.calc.becas.cache.DataScheduleSystem;
import edu.calc.becas.cache.service.CatalogosSystemaHorarios;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.service.LicenciaturaService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class GrupoServiceImpl implements GrupoService {


    private final LicenciaturaService licenciaturaService;
    private final CicloEscolarService cicloEscolarService;
    private final MessageApplicationProperty messageApplicationProperty;
    private final CatalogosSystemaHorarios catalogosSystemaHorarios;

    public GrupoServiceImpl(MessageApplicationProperty messageApplicationProperty,
                            LicenciaturaService licenciaturaService, CicloEscolarService cicloEscolarService,
                            CatalogosSystemaHorarios catalogosSystemaHorarios) {
        this.licenciaturaService = licenciaturaService;
        this.cicloEscolarService = cicloEscolarService;
        this.messageApplicationProperty = messageApplicationProperty;
        this.catalogosSystemaHorarios = catalogosSystemaHorarios;
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
    public WrapperData<Grupo> getAllByStatusAndOneParam(int page, int pageSize, String status, String licenciatura) throws GenericException {
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

    @Override
    public Grupo getGrupoByClave(String cveGrupo) throws GenericException {
        List<Grupo> grupos = this.getAllByStatusAndOneParam(0, 0, null, null).getData();
        Grupo grupoFound = null;
        for (Grupo grupo : grupos) {
            if (grupo.getCveGrupo().equalsIgnoreCase(cveGrupo)) {
                grupoFound = grupo;
            }
        }
        return grupoFound;
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

        return catalogosSystemaHorarios.getAllAllFromScheduledSystem(cicloEscolarVo);
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


}
