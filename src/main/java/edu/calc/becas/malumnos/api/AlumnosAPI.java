package edu.calc.becas.malumnos.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.malumnos.service.AlumnosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static edu.calc.becas.common.utils.Constant.*;


@RestController
@RequestMapping("/alumnos")
@Api(description = "Servicios para administración de Inscripcion de alumnos a Actividades ")
public class AlumnosAPI {

    private final AlumnosService alumnosService;

    @Autowired
    public AlumnosAPI(AlumnosService alumnosService) {
        this.alumnosService = alumnosService;
    }

    @GetMapping
    @ApiOperation(value = "Obtiene el listado de alumnos")
    public WrapperData getAll(
            @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE)
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



  @GetMapping("/cargas")
  @ApiOperation(value = "Obtiene el listado de alumnos")
  public WrapperData getAlumnoCargado(
    @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE)
    @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
    @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
    @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
    @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS)
    @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,
    @ApiParam(value = "Id de la actividad a la que perteneco el alumno", defaultValue = DEFAULT_ESTATUS)
    @RequestParam(value = "cicloEscolar", defaultValue = DEFAULT_ESTATUS, required = false) String cicloEscolar) {

    if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
      pageSize = ITEMS_FOR_PAGE;
    }

    return alumnosService.getAllByStatusLoad(Integer.parseInt(page), Integer.parseInt(pageSize), status, cicloEscolar);
  }


}
