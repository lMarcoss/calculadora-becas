package edu.calc.becas.excel.write.report.beca.periodo;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Generate report xlsx by periodo
 * Date: 06/05/20
 */
public final class ExcelWritterReportBeca {

    private ExcelWritterReportBeca() {
    }

    /***
     *
     * @param wrapperDataReport
     * @return File report generated
     * @throws IOException
     */
    public static InputStreamResource export(WrapperData<ReporteBecaPeriodo> wrapperDataReport) throws IOException {

        Workbook workbook = generateExcel(wrapperDataReport);

        InputStream inputStream = write(workbook);

        return new InputStreamResource(inputStream);
    }

    /***
     *
     * @param workbook
     * @return Content report
     * @throws IOException
     */
    private static InputStream write(Workbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        byte[] barray = bos.toByteArray();
        return new ByteArrayInputStream(barray);
    }

    /***
     *
     * @return Generate content excel
     * @param wrapperDataReport
     */
    private static Workbook generateExcel(WrapperData<ReporteBecaPeriodo> wrapperDataReport) {

        Workbook workbook = new HSSFWorkbook();

        createBecaPercentPage(workbook, wrapperDataReport);


        return workbook;
    }

    private static Font createFont(Workbook workbook, IndexedColors color, boolean bold) {
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setItalic(false);
        font.setColor(color.getIndex());
        font.setBold(bold);

        return font;
    }

    /**
     * Create beca percent page
     *
     * @param workbook
     * @param wrapperDataReport
     */
    private static void createBecaPercentPage(Workbook workbook, WrapperData<ReporteBecaPeriodo> wrapperDataReport) {

        Sheet pageBecaPercent = workbook.createSheet("Porcentaje de Becas");

        pageBecaPercent.setColumnWidth(2, 5000 * 3);
        createHeaderTitleRow(pageBecaPercent, workbook);
        createHeaderColumnData(pageBecaPercent, workbook);
        int row = 6;
        // TODO: create body report
    }

    /**
     * Crea la cabcecera de los datos del reporte
     *
     * @param pageBecaPercent
     * @param workbook
     */
    private static void createHeaderColumnData(Sheet pageBecaPercent, Workbook workbook) {
        Font font = createFont(workbook, IndexedColors.BLACK, false);


        CellStyle cellStyleHeaderYellowNormal = createCellStyle(workbook, font, IndexedColors.LIGHT_YELLOW, false, false);
        CellStyle cellStyleHeaderYellowRotation90 = createCellStyle(workbook, font, IndexedColors.LIGHT_YELLOW, true, false);

        int positionCell = 0;
        int positionRow = 2;
        int endPositionRow = 5;
        Row row = pageBecaPercent.createRow(positionRow);
        row.setHeight((short) 800);

        // NUMERO
        addCellHeaderData(row, "N\u00famero", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, positionCell, pageBecaPercent);
        //MATRICULA
        addCellHeaderData(row, "Matr\u00edcula", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "Nombre", cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "Programa Educativo", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "Grupo", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);

        //talleres
        addCellHeaderData(row, "1er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "2do Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "3er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "80%", cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "10%", cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);

        Row row3 = pageBecaPercent.createRow(positionRow + 2);
        row3.setHeight((short) 800);
        addCellHeaderData(row3, "0%", cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//10%/0

        // biblioteca
        addCellHeaderData(row, "1er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "2do Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "3er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "80%", cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "15%", cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);
        addCellHeaderData(row3, "0%", cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//15%/0


        // sala
        addCellHeaderData(row, "1er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "2do Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "3er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "80%", cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "15%", cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);
        addCellHeaderData(row3, "0%", cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//15%/0

    }

    private static void addCellHeaderData(Row row, String value, CellStyle cellStyle, int positionRow, int endPositionRow, int positionCell, Sheet pageBecaPercent) {
        Cell cellNumeroConsecutivo = row.createCell(positionCell);
        cellNumeroConsecutivo.setCellValue(value);
        cellNumeroConsecutivo.setCellStyle(cellStyle);
        pageBecaPercent.addMergedRegion(new CellRangeAddress(positionRow, endPositionRow, positionCell, positionCell));
    }


    /**
     * Crea la cabecera principal del excel
     *
     * @param pageBecaPercent
     * @param workbook
     */
    private static void createHeaderTitleRow(Sheet pageBecaPercent, Workbook workbook) {

        Font font = createFont(workbook, IndexedColors.WHITE, true);
        CellStyle cellStyleHeaderGreen = createCellStyle(workbook, font, IndexedColors.GREEN, false, true);

        Row row = pageBecaPercent.createRow(0);
        row.setHeight((short) 800);
        Cell cellDatosGeneralesAlumno = row.createCell(0);
        cellDatosGeneralesAlumno.setCellValue("DATOS GENERALES DEL ALUMNO");
        cellDatosGeneralesAlumno.setCellStyle(cellStyleHeaderGreen);
        CellRangeAddress cellRangeAddressDatosAlumnos = new CellRangeAddress(0, 1, 0, 4);
        pageBecaPercent.addMergedRegion(cellRangeAddressDatosAlumnos);


        // actividades extraescolares
        Cell cellActividadesExtraExcolares = row.createCell(5);
        cellActividadesExtraExcolares.setCellValue("ACTIVIDADES EXTRAESCOLARES");
        cellActividadesExtraExcolares.setCellStyle(cellStyleHeaderGreen);
        pageBecaPercent.addMergedRegion(new CellRangeAddress(0, 0, 5, 19));


        // talleres y clubes
        Row rowSubtitle = pageBecaPercent.createRow(1);
        rowSubtitle.setHeight((short) 800);
        Cell cellTalleresYclubes = rowSubtitle.createCell(5);
        cellTalleresYclubes.setCellValue("Talleres y Clubes (Lic)\nEstancia concluida (DEP)");
        cellTalleresYclubes.setCellStyle(cellStyleHeaderGreen);
        pageBecaPercent.addMergedRegion(new CellRangeAddress(1, 1, 5, 9));

        Cell cellBiblioteca = rowSubtitle.createCell(10);
        cellBiblioteca.setCellValue("Biblioteca (Lic)\nAsist. a Seminario (DEP)");
        cellBiblioteca.setCellStyle(cellStyleHeaderGreen);
        pageBecaPercent.addMergedRegion(new CellRangeAddress(1, 1, 10, 14));

        Cell cellSalaDeComputo = rowSubtitle.createCell(15);
        cellSalaDeComputo.setCellValue("Sala de Cómputo (Lic)\nInglés (DEP)");
        cellSalaDeComputo.setCellStyle(cellStyleHeaderGreen);
        pageBecaPercent.addMergedRegion(new CellRangeAddress(1, 1, 15, 19));


    }

    private static CellStyle createCellStyle(Workbook workbook, Font font, IndexedColors color, boolean rotation, boolean wrapperText) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(color.getIndex());
        cellStyle.setFont(font);
        /*cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());*/
        if (rotation) {
            cellStyle.setRotation((short) 90);
        }
        if (wrapperText) {
            cellStyle.setWrapText(true);
        }
        return cellStyle;
    }

}
