package edu.calc.becas.malumnos.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.malumnos.service.AlumnosService;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static edu.calc.becas.common.utils.Constant.*;


/**
 * Api para exponer servicios de consulta de alumnos
 *
 * @author Luis Angel Perez Herrera
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 2019-06-16
 */
@RestController
@RequestMapping("/alumnos")
@Api(description = "Servicios para administraci\u00f3n de Inscripcion de alumnos a Actividades ")
@Slf4j
public class AlumnosAPI {

    private final AlumnosService alumnosService;

    @Autowired
    public AlumnosAPI(AlumnosService alumnosService) {
        this.alumnosService = alumnosService;
    }

    @GetMapping
    @ApiOperation(value = "Obtiene el listado de alumnos")
    public WrapperData getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,
            @ApiParam(value = "Id de la actividad a la que perteneco el alumno", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "actividad", defaultValue = DEFAULT_ESTATUS, required = false) String actividad) {

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }

        return alumnosService.getAllByStatusAndOneParam(Integer.parseInt(page), Integer.parseInt(pageSize), status, actividad);
    }

    @PostMapping("/actividades")
    @ApiOperation(value = "Inserta un alumno con su actividad en la base de datos")
    public Alumno add(@ApiParam(value = "Realiza el insert a la tabla de alumnos y actividades", defaultValue = "0") @RequestBody Alumno alumno) throws GenericException {
        alumnosService.add(alumno);
        return alumno;
    }

    @GetMapping("/info")
    @ApiOperation(value = "Obtiene datos del alumno")
    public Usuario getAlumno(@RequestParam(value = "matricula", defaultValue = DEFAULT_ESTATUS, required = false) String matricula) throws GenericException {
        return alumnosService.getUserInfo(matricula);
    }


    @GetMapping("/cargas")
    @ApiOperation(value = "Obtiene el listado de alumnos")
    public WrapperData getAlumnoCargado(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "status", defaultValue = ALL_ITEMS, required = false) String status,

            @ApiParam(value = "Id de la actividad a la que perteneco el alumno", defaultValue = ALL_ITEMS)
            @RequestParam(value = "cicloEscolar", defaultValue = ALL_ITEMS, required = false) String cicloEscolar,

            @ApiParam(value = "Id de la actividad a la que perteneco el alumno", defaultValue = ALL_ITEMS)
            @RequestParam(value = "licenciatura", defaultValue = ALL_ITEMS, required = false) String licenciatura,

            @ApiParam(value = "Id de la actividad a la que perteneco el alumno", defaultValue = ALL_ITEMS)
            @RequestParam(value = "grupo", defaultValue = ALL_ITEMS, required = false) String grupo) {

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }

        return alumnosService.getAllByStatusLoad(Integer.parseInt(page), Integer.parseInt(pageSize), status, cicloEscolar, licenciatura, grupo);
    }

    @PutMapping
    @ApiOperation(value = "Actualiza los datos del alumno")
    public Alumno updateAlumno(@RequestBody Alumno alumno) {
        return this.alumnosService.update(alumno);
    }
}
