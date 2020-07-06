package edu.calc.becas.mconfiguracion.horario.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mcatalogos.actividades.service.ActividadesService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * Universidad de la Sierra Sur (UNSIS)
 * Description: expone servicios de horario actividad
 * Date: 28/06/20
 */
@RestController
@RequestMapping
@Slf4j
public class HorarioActividadAPI {
    private final UserRequestService userRequestService;
    private final ActividadesService actividadesService;
    private final CicloEscolarService cicloEscolarService;

    public HorarioActividadAPI(UserRequestService userRequestService, ActividadesService actividadesService, CicloEscolarService cicloEscolarService) {
        this.userRequestService = userRequestService;
        this.actividadesService = actividadesService;
        this.cicloEscolarService = cicloEscolarService;
    }

    @GetMapping("/actividades/detalle")
    @ApiOperation(value = "Obtiene el listado de horarios de las actividades")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,

            @ApiParam(value = "Identificador de la actividad a recuperar el detalle", defaultValue = ALL_ITEMS)
            @RequestParam(value = "actividad", defaultValue = ALL_ITEMS, required = false) String idActividad,

            @ApiParam(value = "Identificador del ciclo escolar asociado a la actividad", defaultValue = ALL_ITEMS)
            @RequestParam(value = "ciclo", defaultValue = ALL_ITEMS, required = false) String idCiclo,

            @ApiParam(value = "Encargado de la actividad", defaultValue = ALL_ITEMS)
            @RequestParam(value = "username", defaultValue = ALL_ITEMS, required = false) String username,
            HttpServletRequest httpServlet) {

        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }

        return actividadesService.getAllDetalle(Integer.parseInt(page), Integer.parseInt(pageSize), idActividad, idCiclo, status, username, userLogin);
    }

    @PostMapping("/actividades/detallehoras")
    @ApiOperation(value = "Registra un nuevo horario de una actividad extra-escolar")
    public DetalleActividadVo add(@ApiParam(value = "Detalle de horario para una actividad", defaultValue = "0")
                                  @RequestBody DetalleActividadVo detalle,
                                  HttpServletRequest httpServlet) throws Exception {
        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        detalle.setAgregadoPor(userLogin.getUsername());
        try {
            CicloEscolarVo cicloEscolarVo;
            cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();

            detalle.setIdCicloEscolar(cicloEscolarVo.getClave());
            detalle.setCicloEscolar(cicloEscolarVo.getNombre());

            return actividadesService.add(detalle);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e.getMessage());
        }

    }


    @PutMapping("/actividades/detallehoras")
    @ApiOperation(value = "Actualiza los datos de un horario de una actividad extra-escolar")
    public DetalleActividadVo modify(@ApiParam(value = "Detalle de hora para una actividad", defaultValue = "0")
                                     @RequestBody DetalleActividadVo detalle, HttpServletRequest httpServlet) throws GenericException {
        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        detalle.setActualizadoPor(userLogin.getUsername());
        try {
            return actividadesService.updateDetail(detalle);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e.getMessage());
        }
    }
}
