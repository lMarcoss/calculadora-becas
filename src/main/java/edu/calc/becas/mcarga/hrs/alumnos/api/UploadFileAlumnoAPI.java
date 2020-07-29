package edu.calc.becas.mcarga.hrs.alumnos.api;


import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcarga.hrs.UploadFileAPI;
import edu.calc.becas.mcarga.hrs.alumnos.model.ProcessAlumnos;
import edu.calc.becas.mcarga.hrs.alumnos.service.CargaAlumnosPeriodosService;
import edu.calc.becas.mcarga.hrs.read.files.ReadFile;
import edu.calc.becas.mcarga.hrs.read.files.model.ProcessedFile;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static edu.calc.becas.common.utils.Message.MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER;
import static edu.calc.becas.utils.ExtensionFile.XLSX_EXTENSION;
import static edu.calc.becas.utils.ExtensionFile.XLS_EXTENSION;

@RestController
@RequestMapping("/alumnos-periodo")
@Api(description = "Servicios para carga de alumnos en el periodo en curso")
public class UploadFileAlumnoAPI extends UploadFileAPI {

  protected CargaAlumnosPeriodosService cargaAlumnosPeriodosService;

  @Autowired
  private CicloEscolarService cicloEscolarService;

  @Autowired
  private ParcialService parcialService;

  @Value("${location.file}")
  String locationFile;

  @Autowired
  public UploadFileAlumnoAPI(@Qualifier("cargaAlumnosPeriodosService") CargaAlumnosPeriodosService cargaAlumnosPeriodosService) {
      this.cargaAlumnosPeriodosService = cargaAlumnosPeriodosService;
  }



  @PostMapping("/uploadFile")
  @ApiOperation(value = "Carga de archivo")
  public ProcessedFile uploadFile(@RequestParam("file") MultipartFile file,
                                     @RequestParam("nombreLicenciatura") String nombreLicenciatura,
                                     @RequestParam("cveLicenciatura") String cveLicenciatura,
                                     @RequestParam("cveGrupo") String grupoFilterSelected
                                     ) throws GenericException {
    String pathfile = saveFile(file);
    Workbook pages = ReadFile.pages(pathfile);
    CommonData commonData = new CommonData();

    commonData.setAgregadoPor("ADMIN");
    commonData.setActualizadoPor("ADMIN");
    Parcial parcialActual = parcialService.getParcialActual();
    CicloEscolarVo cicloEscolarActual = cicloEscolarService.getCicloEscolarActual();
    Licenciatura lic = new Licenciatura();
    lic.setCveLicenciatura(cveLicenciatura);
    lic.setNombreLicenciatura(nombreLicenciatura);

    ProcessAlumnos resultProcessed = cargaAlumnosPeriodosService.processData(pages, commonData, parcialActual, cicloEscolarActual, lic, grupoFilterSelected);
    return ProcessedFile.builder()
      .error(false)
      .file(pathfile)
      .message(String.format(MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER, resultProcessed.getProcessSuccess()))
      .idFile(1)
      .registrosError(resultProcessed.getAlumno())
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
