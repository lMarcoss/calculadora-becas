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

import static edu.calc.becas.common.utils.Message.MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER;
import static edu.calc.becas.common.utils.Message.MESSAGE_ROW_PROCESSED_ROOM_COMPUTER;
import static edu.calc.becas.utils.ExtensionFile.XLSX_EXTENSION;
import static edu.calc.becas.utils.ExtensionFile.XLS_EXTENSION;

/**
 * @author Marcos Santiago Leonardos
 * Universidad de la Sierra Sur (UNSIS)
 * Description: api genérico para cargar y guardar archivo
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

    @PostMapping("/upload/parcial/{parcial}")
    @ApiOperation(value = "Carga de archivo")
    public ProcessedFile uploadFactura(@RequestParam("file") MultipartFile file, @PathVariable int parcial) throws GenericException {
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
            message = String.format(MESSAGE_ROW_PROCESSED_ROOM_COMPUTER, 1);
        } else {
            message = String.format(MESSAGE_ROWS_PROCESSED_ROOM_COMPUTER, resultProcessed);
        }
        return ProcessedFile.builder()
                .error(false)
                .file(pathfile)
                .message(message)
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
