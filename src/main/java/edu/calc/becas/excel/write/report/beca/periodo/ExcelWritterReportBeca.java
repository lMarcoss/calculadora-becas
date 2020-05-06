package edu.calc.becas.excel.write.report.beca.periodo;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mreporte.actividades.percent.beca.model.ReporteBecaPeriodo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

        XSSFWorkbook workbook = generateExcel();

        InputStream inputStream = write(workbook);

        return new InputStreamResource(inputStream);
    }

    /***
     *
     * @param workbook
     * @return Content report
     * @throws IOException
     */
    private static InputStream write(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        byte[] barray = bos.toByteArray();
        return new ByteArrayInputStream(barray);
    }

    /***
     *
     * @return Generate content excel
     */
    private static XSSFWorkbook generateExcel() {

        XSSFWorkbook workbook = new XSSFWorkbook();

        createBecaPercentPage(workbook);


        return workbook;
    }

    /**
     * Create beca percent page
     *
     * @param workbook
     */
    private static void createBecaPercentPage(XSSFWorkbook workbook) {
        workbook.createSheet("Porcentaje de Becas");

        /*DataFormat format = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("#.###############"));

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        int colNum = 0;
        String[] var9 = headers;
        int var10 = headers.length;

        for (int var11 = 0; var11 < var10; ++var11) {
            String header = var9[var11];
            createCell(row, colNum++, header, (CellStyle) null);
        }
*/
    }
}
