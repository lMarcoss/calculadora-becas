package edu.calc.becas.mcarga.hrs;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 5/13/19
 */
public interface ProcessHoursService {
    int processData(Workbook pages, CommonData commonData, Parcial parcialActual,
                    CicloEscolarVo cicloEscolarActual) throws GenericException;
}
