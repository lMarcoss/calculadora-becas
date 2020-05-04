package edu.calc.becas.mcarga.hrs;

import edu.calc.becas.mcarga.hrs.read.files.model.CellFile;
import edu.calc.becas.mcarga.hrs.read.files.model.RowFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.mcarga.hrs.constants.ConstantsProcessedHrs.VALUE_FORMULA;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: read rows and cell file
 * Date: 6/3/19
 */
public class ProcessRow {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessRow.class);

    protected List<RowFile> readRows(Workbook pages) {

        LOG.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

        List<RowFile> rows = new ArrayList<>();
        for (Sheet sheet : pages) {
            LOG.info("PAGE:  " + sheet.getSheetName());

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

  protected List<RowFile> readRowsAlumnos(Workbook pages) {

    LOG.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

    List<RowFile> rows = new ArrayList<>();
    for (Sheet sheet : pages) {
      LOG.info("PAGE:  " + sheet.getSheetName());
      int rowNum =0;
      for (Row row : sheet) {
        if(rowNum!=0){
          RowFile rowFile = new RowFile();

          List<CellFile> cells = new ArrayList<>();

          CellFile cellFileTmp = new CellFile();
          cellFileTmp.setValue(
            sheet.getSheetName()
          );
          cells.add(cellFileTmp);

          for (Cell cell : row) {
            CellFile cellFile = new CellFile();

            cell.setCellType(1);
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
        rowNum++;
      }
    }
    return rows;
  }

  protected List<RowFile> readRowsAlumnosReportes(Workbook pages) {

    LOG.info("NUMBERS PAGES: " + String.valueOf(pages.getNumberOfSheets()));

    List<RowFile> rows = new ArrayList<>();
    for (Sheet sheet : pages) {
      LOG.info("PAGE:  " + sheet.getSheetName());
      int rowNum =0;
      for (Row row : sheet) {
        if(rowNum>=9){
          RowFile rowFile = new RowFile();

          List<CellFile> cells = new ArrayList<>();

          CellFile cellFileTmp = new CellFile();
          cellFileTmp.setValue(
            sheet.getSheetName()
          );
          cells.add(cellFileTmp);

          for (Cell cell : row) {
            CellFile cellFile = new CellFile();

            cell.setCellType(1);
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
