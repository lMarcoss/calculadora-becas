package edu.calc.becas.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class UtilDate {
    public static String PATTERN_GUION = "dd-MM-yyyy";
    public static String PATTERN_GUION_INVERSE = "yyyy-MM-yy";
    public static String PATTERN_DIAG = "dd/MM/yyyy";
    private UtilDate() {
    }

    /**
     * @param fecha
     * @param daysSum
     * @return suma días a una fecha
     */
    public static Date getNextDayBySum(Date fecha, int daysSum) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE, daysSum);
        return (Date) c.getTime().clone();
    }

    /**
     * @param fecha
     * @return convierte una fecha con formato @format a fecha Date
     * @throws Exception
     */
    public static Date convertToDate(String fecha, String format) throws Exception {
        SimpleDateFormat sdfrmt;
        if(format == null){
            sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
        }else {
            sdfrmt = new SimpleDateFormat(format);
        }

        sdfrmt.setLenient(false);
        try {
            return sdfrmt.parse(fecha);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }


    /**
     * @param fecha
     * @return convierte una fecha DAte a string dd/MM/yyyy
     */
    public static String convertDateToString(Date fecha, String pattern) {
        SimpleDateFormat formato = new SimpleDateFormat(pattern);
        return formato.format(fecha);
    }

    public static String convertMonthToMonthDesc(int month){
        String monthDesc;
        switch (month){
            case 1: monthDesc = "ENE"; break;
            case 2: monthDesc = "FEB"; break;
            case 3: monthDesc = "MAR"; break;
            case 4: monthDesc = "ABR"; break;
            case 5: monthDesc = "MAY"; break;
            case 6: monthDesc = "JUN"; break;
            case 7: monthDesc = "JUL"; break;
            case 8: monthDesc = "AGO"; break;
            case 9: monthDesc = "SEPT"; break;
            case 10: monthDesc = "OCT"; break;
            case 11: monthDesc = "NOV"; break;
            default: monthDesc = "DIC";
            break;
        }
        return monthDesc;
    }

    public static Date getDateToday() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateToday = formatter.format(date);
        return convertToDate(dateToday, PATTERN_DIAG);
    }
}
