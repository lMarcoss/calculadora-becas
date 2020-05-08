package edu.calc.becas.excel.write.report.beca.periodo;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.defpercentbeca.model.DefPorcentajeActividad;
import edu.calc.becas.mreporte.actividades.percent.beca.model.AlumnoReporteBecaPeriodo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Generate report xlsx by periodo
 * Date: 06/05/20
 */
@Slf4j
public final class ExcelWritterReportBeca {

    private ExcelWritterReportBeca() {
    }

    /***
     *
     * @param wrapperDataReport
     * @param defPorcentajeActividad
     * @return File report generated
     * @throws IOException
     */
    public static InputStreamResource export(WrapperData<AlumnoReporteBecaPeriodo> wrapperDataReport,
                                             DefPorcentajeActividad defPorcentajeActividad) throws IOException {

        Workbook workbook = generateExcel(wrapperDataReport, defPorcentajeActividad);

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
     * @param defPorcentajeActividad
     */
    private static Workbook generateExcel(WrapperData<AlumnoReporteBecaPeriodo> wrapperDataReport,
                                          DefPorcentajeActividad defPorcentajeActividad) {

        Workbook workbook = new XSSFWorkbook();

        createBecaPercentPage(workbook, wrapperDataReport, defPorcentajeActividad);


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
     * @param defPorcentajeActividad
     */
    private static void createBecaPercentPage(Workbook workbook, WrapperData<AlumnoReporteBecaPeriodo> wrapperDataReport,
                                              DefPorcentajeActividad defPorcentajeActividad) {

        Sheet pageBecaPercent = workbook.createSheet("Porcentaje de Becas");

        pageBecaPercent.setColumnWidth(1, 5000);
        pageBecaPercent.setColumnWidth(2, 5000 * 3);
        pageBecaPercent.setColumnWidth(3, 3000);
        pageBecaPercent.setColumnWidth(4, 3000);
        createHeaderTitleRow(workbook, pageBecaPercent);
        createHeaderColumnData(workbook, pageBecaPercent, defPorcentajeActividad);
        createBodyreportData(workbook, pageBecaPercent, wrapperDataReport);
    }

    private static void createBodyreportData(Workbook workbook, Sheet pageBecaPercent,
                                             WrapperData<AlumnoReporteBecaPeriodo> wrapperDataReport) {
        pageBecaPercent.createRow(6);

        List<AlumnoReporteBecaPeriodo> listDataReport = wrapperDataReport.getData();

        if (!listDataReport.isEmpty()) {
            Font fontColorBlack = createFont(workbook, IndexedColors.BLACK, false);
            CellStyle cellStyleWhite = createCellStyle(workbook, fontColorBlack, IndexedColors.WHITE, false, false);

            int finalNumRow = 7;


            listDataReport.forEach(alumnoReporteBecaPeriodo -> {
                Row row = pageBecaPercent.createRow(finalNumRow + alumnoReporteBecaPeriodo.getIndex());

                for (ColumnWriteExcel col : ColumnWriteExcel.values()) {

                    try {
                        addCellData(row, cellStyleWhite, alumnoReporteBecaPeriodo, col);
                    } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
                        log.error(String.format("Error add property %s from alumno %s. Error %s",
                                col.getNamePropertyCol(), alumnoReporteBecaPeriodo.getMatricula(), e.getMessage()));
                        log.error(ExceptionUtils.getStackTrace(e));

                    }

                }
            });
        }


    }

    private static void addCellData(Row row, CellStyle cellStyle, AlumnoReporteBecaPeriodo alumnoReporteBecaPeriodo,
                                    ColumnWriteExcel col)
            throws NoSuchFieldException, IllegalAccessException, ClassCastException {

        Cell cell = row.createCell(col.ordinal());

        Field property = alumnoReporteBecaPeriodo.getClass().getDeclaredField(col.getNamePropertyCol());
        property.setAccessible(true);

        if (!col.isNumber()) {
            String value = null;
            if (col.isPercent()) {
                value = property.get(alumnoReporteBecaPeriodo) + "%";
            } else if (col.isString()) {
                value = (String) property.get(alumnoReporteBecaPeriodo);
            }

            cell.setCellValue(value);
        } else {
            // index
            int index = (Integer) property.get(alumnoReporteBecaPeriodo);
            cell.setCellValue(index + 1);
        }


        cell.setCellStyle(cellStyle);
    }


    /**
     * Crea la cabcecera de los datos del reporte
     *
     * @param workbook
     * @param pageBecaPercent
     * @param defPorcentajeActividad
     */
    private static void createHeaderColumnData(Workbook workbook, Sheet pageBecaPercent,
                                               DefPorcentajeActividad defPorcentajeActividad) {
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
        String minimoRequerido = defPorcentajeActividad.getPorcentajeBecaTaller().getPorcentajeMinimoRequerido() + "%";
        String maximoPorcentajeAAlcanzar = defPorcentajeActividad.getPorcentajeBecaTaller().getPorcentajeBecaActividadCumplida() + "%";
        String porcentajeActividadIncumplida = defPorcentajeActividad.getPorcentajeBecaTaller().getPorcentajeBecaActividadIncumplida() + "%";
        addCellHeaderData(row, minimoRequerido, cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, maximoPorcentajeAAlcanzar, cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);

        Row row3 = pageBecaPercent.createRow(positionRow + 2);
        row3.setHeight((short) 800);
        addCellHeaderData(row3, porcentajeActividadIncumplida, cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//10%/0

        // biblioteca
        addCellHeaderData(row, "1er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "2do Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "3er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        minimoRequerido = defPorcentajeActividad.getPorcentajeBecaBiblioteca().getPorcentajeMinimoRequerido() + "%";
        maximoPorcentajeAAlcanzar = defPorcentajeActividad.getPorcentajeBecaBiblioteca().getPorcentajeBecaActividadCumplida() + "%";
        porcentajeActividadIncumplida = defPorcentajeActividad.getPorcentajeBecaBiblioteca().getPorcentajeBecaActividadIncumplida() + "%";
        addCellHeaderData(row, minimoRequerido, cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, maximoPorcentajeAAlcanzar, cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);
        addCellHeaderData(row3, porcentajeActividadIncumplida, cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//15%/0


        // sala
        addCellHeaderData(row, "1er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "2do Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, "3er Parcial", cellStyleHeaderYellowRotation90, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        minimoRequerido = defPorcentajeActividad.getPorcentajeBecaSala().getPorcentajeMinimoRequerido() + "%";
        maximoPorcentajeAAlcanzar = defPorcentajeActividad.getPorcentajeBecaSala().getPorcentajeBecaActividadCumplida() + "%";
        porcentajeActividadIncumplida = defPorcentajeActividad.getPorcentajeBecaSala().getPorcentajeBecaActividadIncumplida() + "%";
        addCellHeaderData(row, minimoRequerido, cellStyleHeaderYellowNormal, positionRow, endPositionRow, ++positionCell, pageBecaPercent);
        addCellHeaderData(row, maximoPorcentajeAAlcanzar, cellStyleHeaderYellowNormal, positionRow, positionRow + 1, ++positionCell, pageBecaPercent);
        addCellHeaderData(row3, porcentajeActividadIncumplida, cellStyleHeaderYellowNormal, positionRow + 2, positionRow + 3, positionCell, pageBecaPercent);//15%/0

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
    private static void createHeaderTitleRow(Workbook workbook, Sheet pageBecaPercent) {

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
