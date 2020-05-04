package edu.calc.becas.mseguridad.menu.dao;

final class QueriesMenu {
    private QueriesMenu() {
    }

    static final String QRY_GET_PARENTS_BY_USER = "SELECT ID_MENU, ID_PADRE, NOMBRE, ICON, URL, ESTATUS, ORDER_MENU FROM MENU WHERE ID_PADRE IS NULL AND ESTATUS = 'S' ORDER BY ORDER_MENU";
    static final String QRY_GET_CHLIDS_BY_PARENT =
            "SELECT M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL, M.ORDER_MENU" +
                    "\nFROM MENU M," +
                    "\n     MENU_ROL MR," +
                    "\n     ROLES R," +
                    "\n     USUARIOS U" +
                    "\nWHERE M.ID_PADRE = ?" +
                    "\n  AND M.ID_MENU = MR.ID_MENU" +
                    "\n  AND MR.ID_ROL = R.ID_ROL" +
                    "\n  AND R.ID_ROL = U.ID_ROL" +
                    "\n  AND M.ESTATUS = 'S'" +
                    "\n  AND MR.ESTATUS = 'S'" +
                    "\n  AND R.ESTATUS = 'S'" +
                    "\n  AND U.ESTATUS = 'S'" +
                    "\n  AND U.USERNAME = ?" +
                    "\nUNION" +
                    "\nSELECT M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL, M.ORDER_MENU" +
                    "\nFROM MENU M," +
                    "\n     MENU_ROL MR," +
                    "\n     ROLES R," +
                    "\n     ALUMNOS U" +
                    "\nWHERE M.ID_PADRE = ?" +
                    "\n  AND M.ESTATUS = 'S'" +
                    "\n  AND MR.ESTATUS = 'S'" +
                    "\n  AND R.ESTATUS = 'S'" +
                    "\n  AND U.ESTATUS = 'S'" +
                    "\n  AND M.ID_MENU != M.ID_PADRE" +
                    "\n  AND M.ID_MENU = MR.ID_MENU" +
                    "\n  AND MR.ID_ROL = R.ID_ROL" +
                    "\n  AND R.ID_ROL = 3" +
                    "\n  AND U.MATRICULA = ?" +
                    "\n  ORDER BY ORDER_MENU";
}
