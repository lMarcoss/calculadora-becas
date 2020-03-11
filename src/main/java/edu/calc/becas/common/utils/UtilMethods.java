package edu.calc.becas.common.utils;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 11/03/20
 */
public final class UtilMethods {
    private UtilMethods() {
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().equalsIgnoreCase("");
    }
}
