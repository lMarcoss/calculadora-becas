package edu.calc.becas.mcarga.hrs;

import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 5/13/19
 */
public interface ProcessHoursService {
    void processData(Workbook pages);
}