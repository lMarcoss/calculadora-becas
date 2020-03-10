package edu.calc.becas.mseguridad.menu.dao;

final class QueriesMenu {
    private QueriesMenu() {
    }

    static final String QRY_GET_PARENTS_BY_USER = "SELECT ID_MENU, ID_PADRE, NOMBRE, ICON, URL FROM MENU WHERE ID_MENU = ID_PADRE";
    static final String QRY_GET_CHLIDS_BY_PARENT = "select M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL\n" +
            "from MENU M, MENU_ROL MR, ROLES R, USUARIOS U\n" +
            "WHERE M.ID_PADRE = ? AND M.ID_MENU != M.ID_PADRE AND  M.ID_MENU = MR.ID_MENU AND MR.ID_ROL = R.ID_ROL AND R.ID_ROL  = U.ID_ROL AND U.USERNAME = ? " +
      "union\n" +
      "select M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL\n" +
      "            from MENU M, MENU_ROL MR, ROLES R, ALUMNOS U\n" +
      "            WHERE M.ID_PADRE = ? AND M.ID_MENU != M.ID_PADRE AND  M.ID_MENU = MR.ID_MENU AND MR.ID_ROL = R.ID_ROL AND R.ID_ROL = 3 AND U.MATRICULA= ?";
}
