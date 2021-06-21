package edu.calc.becas.mcarga.hrs;

import edu.calc.becas.mcarga.hrs.read.files.model.CellFile;
import edu.calc.becas.mcarga.hrs.read.files.model.RowFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.mcarga.hrs.constants.ConstantsProcessedHrs.VALUE_FORMULA;

/**
 * Lee las filas y columnas del archivo excel
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 6/3/19
 */
@Slf4j
public class ProcessRow {

    /**
     * Lee las filas de un archivo - archivo de horas de biblioteca
     *
     * @param pages pagina
     * @return registros
     * @author Marcos Santiago Leonardo
     */
    protected List<RowFile> readRows(Workbook pages) {

        log.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

        List<RowFile> rows = new ArrayList<>();
        for (Sheet sheet : pages) {
            log.info("PAGE:  " + sheet.getSheetName());

            for (Row row : sheet) {

                RowFile rowFile = new RowFile();

                List<CellFile> cells = new ArrayList<>();

                for (Cell cell : row) {
                    CellFile cellFile = new CellFile();
                    String value = readCellByType(cell);
                    if (value != null && !value.trim().equalsIgnoreCase("") && value.length() > 0) {
                        cellFile.setValue(
                                value.trim()
                        );

                        cells.add(cellFile);
                    }
                }
                if (!cells.isEmpty() && cells.size() > 8) {
                    rowFile.setCells(cells);
                    rows.add(rowFile);
                }
            }
        }
        return rows;
    }

    /**
     * Lee las filas de una pagina de excel - archivo Alumnos Periodo
     *
     * @param pages pagina
     * @return registros
     * @author Luis Angel Perez
     */
    protected List<RowFile> readRowsAlumnos(Workbook pages) {

        log.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

        List<RowFile> rows = new ArrayList<>();
        for (Sheet sheet : pages) {
            log.info("PAGE:  " + sheet.getSheetName());
            int rowNum = 0;
            for (Row row : sheet) {
                log.info("NUMBERS rows: " + sheet.getLastRowNum());
                if (sheet.getLastRowNum() > 3) {
                    if (rowNum != 0) {
                        RowFile rowFile = new RowFile();

                        List<CellFile> cells = new ArrayList<>();

                        CellFile cellFileTmp = new CellFile();
                        cellFileTmp.setValue(
                                sheet.getSheetName()
                        );
                        cells.add(cellFileTmp);

                        for (Cell cell : row) {
                            CellFile cellFile = new CellFile();

                            cell.setCellType(CellType.forInt(1));
                            String value = readCellByType(cell);
                            if (value != null && !value.trim().equalsIgnoreCase("") && value.length() > 0) {
                                cellFile.setValue(
                                        value.trim()
                                );

                                cells.add(cellFile);
                            }
                        }
                        if (!cells.isEmpty() && cells.size() > 5) {
                            rowFile.setCells(cells);
                            rows.add(rowFile);
                        }
                    }
                }
                rowNum++;
            }
        }
        return rows;
    }

    /**
     * Lee las filas de una pagina del excel - archivo de alumnos a un periodo
     *
     * @param pages pagina
     * @return registros
     * @author Luis Angel Perez
     */
    protected List<RowFile> readRowsAlumnosReportes(Workbook pages) {

        log.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

        List<RowFile> rows = new ArrayList<>();
        for (Sheet sheet : pages) {
            log.info("PAGE:  " + sheet.getSheetName());
            int rowNum = 0;
            for (Row row : sheet) {
                if (rowNum >= 9) {
                    RowFile rowFile = new RowFile();

                    List<CellFile> cells = new ArrayList<>();

                    CellFile cellFileTmp = new CellFile();
                    cellFileTmp.setValue(
                            sheet.getSheetName()
                    );
                    cells.add(cellFileTmp);

                    for (Cell cell : row) {
                        CellFile cellFile = new CellFile();

                        cell.setCellType(CellType.forInt(1));
                        String value = readCellByType(cell);
                        if (value != null && !value.trim().equalsIgnoreCase("") && value.length() > 0) {
                            cellFile.setValue(
                                    value.trim()
                            );

                            cells.add(cellFile);
                        }
                    }
                    if (!cells.isEmpty()) {
                        rowFile.setCells(cells);
                        rows.add(rowFile);
                    }
                }
                rowNum++;
            }
        }
        return rows;
    }

    /**
     * Lee el valor de las celdas por tipo de celda
     *
     * @param cell celda
     * @return valor de la celda
     */
    private String readCellByType(Cell cell) {
        String value = null;
        try {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    //value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case STRING:
                    value = String.valueOf(cell.getRichStringCellValue());
                    break;
                case FORMULA:
                    value = VALUE_FORMULA.name();
                    break;
                case BLANK:
                    break;
                case _NONE:
                    break;
                default:

            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
