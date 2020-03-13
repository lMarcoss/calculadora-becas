package edu.calc.becas.mcatalogos.actividades.api;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcarga.hrs.alumnos.service.CargaAlumnosPeriodosService;
import edu.calc.becas.mcarga.hrs.read.files.ReadFile;
import edu.calc.becas.mcarga.hrs.read.files.model.ProcessedFile;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mcatalogos.actividades.service.ActividadesService;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.*;
import static edu.calc.becas.common.utils.Message.MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER;
import static edu.calc.becas.utils.ExtensionFile.XLSX_EXTENSION;
import static edu.calc.becas.utils.ExtensionFile.XLS_EXTENSION;

@RestController
@RequestMapping("/actividades")
@Api(description = "Servicios para administración de Actividades Extracurriculares")
public class ActividadesAPI {
    private final ActividadesService actividadesService;

    protected CargaAlumnosPeriodosService cargaAlumnosPeriodosService;

    @Autowired
    private CicloEscolarService cicloEscolarService;

    @Autowired
    private ParcialService parcialService;

    @Value("${location.file}")
    String locationFile;

    @Autowired
    public ActividadesAPI(ActividadesService actividadesService, CicloEscolarService cicloEscolarService, CargaAlumnosPeriodosService cargaAlumnosPeriodosService) {
        this.actividadesService = actividadesService;
        this.cicloEscolarService = cicloEscolarService;
        this.cargaAlumnosPeriodosService = cargaAlumnosPeriodosService;
    }


    @GetMapping
    @ApiOperation(value = "Obtiene el listado de Actividades")
    public WrapperData getAll(
            @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,
            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,
            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,
            @ApiParam(value = "Tipo de actividad", defaultValue = ALL_ITEMS)
            @RequestParam(value = "tipo-actividad", defaultValue = ALL_ITEMS, required = false) String tipoActividad,
            @ApiParam(value = "Actividades con horario (S/N)", defaultValue = ALL_ITEMS)
            @RequestParam(value = "sw-horario", defaultValue = ALL_ITEMS, required = false) String swHorario) {

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return actividadesService.getAllByStatusAndTipoActividadHorario(Integer.parseInt(page), Integer.parseInt(pageSize), status, tipoActividad, swHorario);
    }


    @GetMapping("/detalle")
    @ApiOperation(value = "Obtiene el listado de horarios de las actividades")
    public WrapperData getAll(
            @ApiParam(value = "Página a recuperar", defaultValue = DEFAULT_PAGE)
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE, required = false) String page,

            @ApiParam(value = "Registros a recuperar", defaultValue = ALL_ITEMS)
            @RequestParam(value = "pageSize", defaultValue = ALL_ITEMS, required = false) String pageSize,

            @ApiParam(value = "Estatus de los registros a recuperar", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "status", defaultValue = DEFAULT_ESTATUS, required = false) String status,

            @ApiParam(value = "Identificador de la actividad a recuperar el detalle", defaultValue = ALL_ITEMS)
            @RequestParam(value = "actividad", defaultValue = ALL_ITEMS, required = false) String idActividad,

            @ApiParam(value = "Identificador del ciclo escolar asociado a la actividad", defaultValue = DEFAULT_ESTATUS)
            @RequestParam(value = "ciclo", defaultValue = DEFAULT_ESTATUS, required = false) String idCiclo,

            @ApiParam(value = "Encargado de la actividad", defaultValue = ALL_ITEMS)
            @RequestParam(value = "username", defaultValue = ALL_ITEMS, required = false) String username) {

        if (pageSize.equalsIgnoreCase(ALL_ITEMS)) {
            pageSize = ITEMS_FOR_PAGE;
        }
        return actividadesService.getAllDetalle(Integer.parseInt(page), Integer.parseInt(pageSize), idActividad, idCiclo, status, username);
    }

    @PutMapping
    public ActividadVo modifyActividad(@ApiParam(value = "Detalle de hora para una actividad", defaultValue = "0") @RequestBody ActividadVo detalle) {
        detalle.setActualizadoPor("admin");
        return actividadesService.update(
                detalle
        );
    }


    @GetMapping("/list")
    public List<LabelValueData> getActividades() {
        return actividadesService.getActividades();
    }

    @PostMapping
    @ApiOperation(value = "Registra una nueva Actividad")
    public ActividadVo add(@ApiParam(value = "Actividad a registrar", defaultValue = "0") @RequestBody ActividadVo actividad) throws GenericException {
        actividad.setAgregadoPor("Admin");
        return actividadesService.add(actividad);
    }

    @PostMapping("/detallehoras")
    public DetalleActividadVo add(@ApiParam(value = "Detalle de hora para una actividad", defaultValue = "0") @RequestBody DetalleActividadVo detalle) throws Exception {
        detalle.setAgregadoPor("admin");
        try {
            CicloEscolarVo cicloEscolarVo  = new CicloEscolarVo();
            cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();

            detalle.setIdCicloEscolar(cicloEscolarVo.getClave());
            detalle.setCicloEscolar(cicloEscolarVo.getNombre());

            return actividadesService.add(
                    detalle
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }

    }

    @PutMapping("/detallehoras")
    public DetalleActividadVo modify(@ApiParam(value = "Detalle de hora para una actividad", defaultValue = "0") @RequestBody DetalleActividadVo detalle) {
        detalle.setActualizadoPor("admin");
        return actividadesService.udateDetail(
                detalle
        );
    }

  @PostMapping("/uploadFile")
  @ApiOperation(value = "Carga de archivo")
  public ProcessedFile uploadFactura(@RequestParam("file") MultipartFile file
  ) throws GenericException {
    String pathfile = saveFile(file);
    Workbook pages = ReadFile.pages(pathfile);
    CommonData commonData = new CommonData();

    commonData.setAgregadoPor("ADMIN");
    commonData.setActualizadoPor("ADMIN");
    Parcial parcialActual = parcialService.getParcialActual();
    CicloEscolarVo cicloEscolarActual = cicloEscolarService.getCicloEscolarActual();
    Licenciatura lic = new Licenciatura();
    /*lic.setCveLicenciatura(cveLicenciatura);
    lic.setNombreLicenciatura(nombreLicenciatura);*/

    //int resultProcessed = 0;//cargaAlumnosPeriodosService.processData(pages, commonData, parcialActual, cicloEscolarActual, lic);
    int resultProcessed = cargaAlumnosPeriodosService.processDataPorcentajes(pages, commonData, parcialActual, cicloEscolarActual, lic);
    return ProcessedFile.builder()
      .error(false)
      .file(pathfile)
      .message(String.format(MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER, resultProcessed))
      .idFile(1)
      .build();

  }

  private String saveFile(MultipartFile file) throws GenericException {
    String nameFile = createNameFile(file.getOriginalFilename());
    try {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(locationFile + nameFile);
      Files.write(path, bytes);
      return path.toString();
    } catch (IOException e) {
      throw new GenericException(e);
    }
  }

  private String createNameFile(String originalFilename) {
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-HH:mm:ss");
    String strDate = dateFormat.format(date);

    String nameFile;

    if (originalFilename.toUpperCase().endsWith(XLSX_EXTENSION)) {
      nameFile = originalFilename.toUpperCase().replace(XLSX_EXTENSION, "");
      return nameFile.replace(" ", "_") + strDate + XLSX_EXTENSION;
    } else {
      nameFile = originalFilename.toUpperCase().replace(XLS_EXTENSION, "");
      return nameFile.replace(" ", "_") + strDate + XLS_EXTENSION;
    }

  }
}
