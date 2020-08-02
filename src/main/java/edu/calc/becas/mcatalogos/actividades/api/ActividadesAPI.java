package edu.calc.becas.mcatalogos.actividades.api;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcarga.hrs.alumnos.service.CargaAlumnosPeriodosService;
import edu.calc.becas.mcarga.hrs.read.files.ReadFile;
import edu.calc.becas.mcarga.hrs.read.files.model.ProcessedFile;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.service.ActividadesService;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
import static edu.calc.becas.common.utils.Message.MESSAGE_ROWS_PROCESSED;
import static edu.calc.becas.common.utils.Message.MESSAGE_ROW_PROCESSED;
import static edu.calc.becas.utils.ExtensionFile.XLSX_EXTENSION;
import static edu.calc.becas.utils.ExtensionFile.XLS_EXTENSION;

/**
 * API para exponer servcios para administracion de actividades o talleres
 */
@RestController
@RequestMapping("/actividades")
@Api(description = "Servicios para administraci\u00f3n de Actividades Extracurriculares")
@Slf4j
public class ActividadesAPI {
    private final ActividadesService actividadesService;

    private CargaAlumnosPeriodosService cargaAlumnosPeriodosService;
    private final CicloEscolarService cicloEscolarService;
    private final ParcialService parcialService;
    private final UserRequestService userRequestService;

    @Value("${location.file}")
    String locationFile;

    @Autowired
    public ActividadesAPI(ActividadesService actividadesService, CargaAlumnosPeriodosService cargaAlumnosPeriodosService,
                          CicloEscolarService cicloEscolarService, ParcialService parcialService, UserRequestService userRequestService) {
        this.actividadesService = actividadesService;
        this.cicloEscolarService = cicloEscolarService;
        this.cargaAlumnosPeriodosService = cargaAlumnosPeriodosService;
        this.parcialService = parcialService;
        this.userRequestService = userRequestService;
    }

    /**
     * Obtiene el listado de Actividades por filtro
     *
     * @param page          pagina
     * @param pageSize      registros por pagina
     * @param status        estatus
     * @param tipoActividad tipo de actividad
     * @param swHorario     horario
     * @return
     */
    @GetMapping
    @ApiOperation(value = "Obtiene el listado de Actividades")
    public WrapperData<ActividadVo> getAll(
            @ApiParam(value = "P\u00e1gina a recuperar", defaultValue = DEFAULT_PAGE)
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

    /**
     * Actualiza los datos de una actividad
     *
     * @param actividad actividad
     * @return
     */
    @PutMapping
    public ActividadVo modifyActividad(@ApiParam(value = "Detalle de hora para una actividad", defaultValue = "0") @RequestBody ActividadVo actividad) {
        actividad.setActualizadoPor("admin");
        return actividadesService.update(
                actividad
        );
    }

    /**
     * Recupera una lista de clave-valor de las actividades
     *
     * @return
     */
    @GetMapping("/list")
    public List<LabelValueData> getActividades() {
        return actividadesService.getActividades();
    }

    /**
     * Registra una nueva Actividad
     *
     * @param actividad
     * @return
     * @throws GenericException
     */
    @PostMapping
    @ApiOperation(value = "Registra una nueva Actividad")
    public ActividadVo add(@ApiParam(value = "Actividad a registrar", defaultValue = "0") @RequestBody ActividadVo actividad) throws GenericException {
        actividad.setAgregadoPor("Admin");
        return actividadesService.add(actividad);
    }

    /**
     * Carga una lista de alumnos a un periodo
     *
     * @param file
     * @return
     * @throws GenericException
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "Carga de archivo")
    public ProcessedFile uploadFile(@RequestParam("file") MultipartFile file
    ) throws GenericException {
        String pathfile = saveFile(file);
        Workbook pages = ReadFile.pages(pathfile);
        CommonData commonData = new CommonData();

        commonData.setAgregadoPor("ADMIN");
        commonData.setActualizadoPor("ADMIN");
        Parcial parcialActual = parcialService.getParcialActual();
        CicloEscolarVo cicloEscolarActual = cicloEscolarService.getCicloEscolarActual();
        Licenciatura lic = new Licenciatura();

        int resultProcessed = cargaAlumnosPeriodosService.processDataPorcentajes(pages, commonData, parcialActual, cicloEscolarActual, lic);
        String message;
        if (resultProcessed == 1) {
            message = String.format(MESSAGE_ROW_PROCESSED, resultProcessed);
        } else {
            message = String.format(MESSAGE_ROWS_PROCESSED, resultProcessed);
        }

        return ProcessedFile.builder()
                .error(false)
                .file(pathfile)
                .message(message)
                .idFile(1)
                .build();

    }

    /**
     * Guarda el archivo en el sistema
     *
     * @param file
     * @return
     * @throws GenericException
     */
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

    /**
     * Crea un nombre para el archivo
     *
     * @param originalFilename
     * @return
     */
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
