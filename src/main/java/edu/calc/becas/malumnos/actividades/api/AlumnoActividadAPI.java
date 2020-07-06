package edu.calc.becas.malumnos.actividades.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.actividades.service.AlumnoActividadService;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import static edu.calc.becas.common.utils.Constant.*;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Api para exponer servicios de consulta de actividades por alumno(s)
 * Date: 2019-06-16
 */
@RestController
@RequestMapping()
@Api(description = "Servicios para consultar y administrar actividades de alumnos")
public class AlumnoActividadAPI {

    private final AlumnoActividadService alumnoActividadService;
    private CicloEscolarService cicloEscolarService;
    private UserRequestService userRequestService;

    public AlumnoActividadAPI(AlumnoActividadService alumnoActividadService, CicloEscolarService cicloEscolarService,
                              UserRequestService userRequestService) {
        this.alumnoActividadService = alumnoActividadService;
        this.cicloEscolarService = cicloEscolarService;
        this.userRequestService = userRequestService;
    }

    @GetMapping("/alumnos/{matricula}/actividades")
    public ActividadVo getActividadByAlumno(@PathVariable String matricula) throws GenericException {
        return alumnoActividadService.getActividadByAlumno(matricula, cicloEscolarService.getCicloEscolarActual());
    }

    @GetMapping("/alumnos/actividades")
    @ApiOperation(value = "Obtiene el listado de detalle de actividades de los alumnos")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Encargado de la actividad a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "username", defaultValue = ALL_ITEMS, required = false) String username,

            @ApiParam(value = "Identificador de la actividad a recuperar el detalle", defaultValue = ALL_ITEMS)
            @RequestParam(value = "actividad", defaultValue = ALL_ITEMS, required = false) String idActividad,

            @ApiParam(value = "Identificador del ciclo escolar asociado a la actividad", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "ciclo", defaultValue = DEFAULT_ESTATUS, required = false) String idCiclo
    ) {

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return alumnoActividadService.getAllAlumnosByActividad(Integer.parseInt(page), Integer.parseInt(pageSize), idActividad, idCiclo, username);
    }


}
