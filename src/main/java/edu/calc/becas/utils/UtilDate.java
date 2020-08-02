package edu.calc.becas.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Metodos para validacion de fecha
 */
public final class UtilDate {
    public static String PATTERN_GUION = "dd-MM-yyyy";
    public static String PATTERN_GUION_INVERSE = "yyyy-MM-dd";
    public static String PATTERN_DIAG = "dd/MM/yyyy";

    private UtilDate() {
    }

    /**
     * @param fecha   fecha
     * @param daysSum dias a suma a la fecha
     * @return suma dias a una fecha
     */
    public static Date getDateSumDay(Date fecha, int daysSum) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE, daysSum);
        return (Date) c.getTime().clone();
    }

    /**
     * @param fecha fecha
     * @return convierte una fecha con formato @format a fecha Date
     * @throws Exception error de conversion
     */
    public static Date convertToDate(String fecha, String format) throws Exception {
        SimpleDateFormat sdfrmt;
        if (format == null) {
            sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
        } else {
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

    /**
     * Obiene la descripcion de un mes
     *
     * @param month mes
     * @return descripcion en tres caracteres
     */
    public static String convertMonthToMonthDesc(int month) {
        String monthDesc;
        switch (month) {
            case 1:
                monthDesc = "ENE";
                break;
            case 2:
                monthDesc = "FEB";
                break;
            case 3:
                monthDesc = "MAR";
                break;
            case 4:
                monthDesc = "ABR";
                break;
            case 5:
                monthDesc = "MAY";
                break;
            case 6:
                monthDesc = "JUN";
                break;
            case 7:
                monthDesc = "JUL";
                break;
            case 8:
                monthDesc = "AGO";
                break;
            case 9:
                monthDesc = "SEPT";
                break;
            case 10:
                monthDesc = "OCT";
                break;
            case 11:
                monthDesc = "NOV";
                break;
            default:
                monthDesc = "DIC";
                break;
        }
        return monthDesc;
    }

    /**
     * Obtiene la fecha en curso
     *
     * @return fecha en curso
     * @throws Exception error de conversion
     */
    public static Date getDateToday() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateToday = formatter.format(date);
        return convertToDate(dateToday, PATTERN_DIAG);
    }

    /**
     * Valida si una fecha esta entre un rango de fechas
     *
     * @param fechaInicio     fecha inicio del rango
     * @param fechaFin        fecha fin del rango
     * @param fechaAsistencia fecha a validaro
     * @return
     */
    public static boolean isDateBetween(Date fechaInicio, Date fechaFin, Date fechaAsistencia) {
        return fechaAsistencia.equals(fechaInicio) || fechaAsistencia.equals(fechaFin)
                || (fechaAsistencia.after(fechaInicio) && fechaAsistencia.before(fechaFin));
    }
}
