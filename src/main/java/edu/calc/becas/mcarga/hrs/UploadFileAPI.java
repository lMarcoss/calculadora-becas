package edu.calc.becas.mcarga.hrs;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcarga.hrs.read.files.ReadFile;
import edu.calc.becas.mcarga.hrs.read.files.model.ProcessedFile;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static edu.calc.becas.common.utils.Message.MESSAGE_ROWS_PROCESSED;
import static edu.calc.becas.common.utils.Message.MESSAGE_ROW_PROCESSED;
import static edu.calc.becas.utils.ExtensionFile.XLSX_EXTENSION;
import static edu.calc.becas.utils.ExtensionFile.XLS_EXTENSION;

/**
 * API generico para cargar y guardar archivo
 *
 * @author Marcos Santiago Leonardos
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 5/4/19
 */

public class UploadFileAPI {

    protected ProcessHoursService processHoursService;
    @Autowired
    private CicloEscolarService cicloEscolarService;
    @Autowired
    private ParcialService parcialService;

    @Value("${location.file}")
    String locationFile;

    /**
     * Servicio para subir archivo de horas de biblioteca en un parcial en el periodo en curso,
     * <p>
     * Se valida los dias de tolerancia por usuario al subir archivo
     *
     * @param file    archivo
     * @param parcial parcial
     * @return total de registros procesados
     * @throws GenericException error al procesar archivo
     */
    @PostMapping("/upload/parcial/{parcial}")
    @ApiOperation(value = "Carga de archivo")
    public ProcessedFile uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int parcial) throws GenericException {
        String pathfile = saveFile(file);
        Workbook pages = ReadFile.pages(pathfile);
        CommonData commonData = new CommonData();
        commonData.setAgregadoPor("ADMIN");
        commonData.setActualizadoPor("ADMIN");

        CicloEscolarVo cicloEscolarActual = cicloEscolarService.getCicloEscolarActual();

        Parcial parcialCarga = parcialService.getParcialByPeriodoAndParcialOrd(parcial, cicloEscolarActual);

        int resultProcessed = processHoursService.processData(pages, commonData, parcialCarga, cicloEscolarActual);
        String message;
        if (resultProcessed == 1) {
            message = String.format(MESSAGE_ROW_PROCESSED, 1);
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
     * Guarda el archivo
     *
     * @param file archivo
     * @return ruta del archivo guardado
     * @throws GenericException error al guardar archivo
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
     * @param originalFilename archivo-cargado
     * @return nombre archivo
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
