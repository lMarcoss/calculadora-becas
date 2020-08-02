package edu.calc.becas.mcarga.hrs.read.files;

import edu.calc.becas.exceptions.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Lee archivo de excel
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 5/13/19
 */
@Slf4j
public class ReadFile {
    private static final Logger LOG = LoggerFactory.getLogger(ReadFile.class);

    /**
     * Lee un archivo de excel
     *
     * @param pathfile ruta del archivo a leer
     * @return contenido del archivo
     * @throws GenericException error de lectura
     */
    public static Workbook pages(String pathfile) throws GenericException {
        Workbook workbook = null;
        FileInputStream file;
        try {
            file = new FileInputStream(new File(pathfile));
            workbook = WorkbookFactory.create(file);
        } catch (IOException | InvalidFormatException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(e);
        }

        System.out.println("SHEETS: " + workbook.getNumberOfSheets());

        return workbook;

    }
}
