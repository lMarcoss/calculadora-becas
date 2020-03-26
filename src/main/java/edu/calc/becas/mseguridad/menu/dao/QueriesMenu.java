package edu.calc.becas.mseguridad.menu.dao;

final class QueriesMenu {
    private QueriesMenu() {
    }

    static final String QRY_GET_PARENTS_BY_USER = "SELECT ID_MENU, ID_PADRE, NOMBRE, ICON, URL, ESTATUS FROM MENU WHERE ID_MENU = ID_PADRE AND ESTATUS = 'S'";
    static final String QRY_GET_CHLIDS_BY_PARENT =
            "SELECT M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL\n" +
                    "FROM MENU M,\n" +
                    "     MENU_ROL MR,\n" +
                    "     ROLES R,\n" +
                    "     USUARIOS U\n" +
                    "WHERE M.ID_PADRE = ?\n" +
                    "  AND M.ID_MENU != M.ID_PADRE\n" +
                    "  AND M.ID_MENU = MR.ID_MENU\n" +
                    "  AND MR.ID_ROL = R.ID_ROL\n" +
                    "  AND R.ID_ROL = U.ID_ROL\n" +
                    "  AND M.ESTATUS = 'S'\n" +
                    "  AND MR.ESTATUS = 'S'\n" +
                    "  AND R.ESTATUS = 'S'\n" +
                    "  AND U.ESTATUS = 'S'\n" +
                    "  AND U.USERNAME = ?\n" +
                    "UNION\n" +
                    "SELECT M.ID_MENU, M.ID_PADRE, M.NOMBRE, M.URL\n" +
                    "FROM MENU M,\n" +
                    "     MENU_ROL MR,\n" +
                    "     ROLES R,\n" +
                    "     ALUMNOS U\n" +
                    "WHERE M.ID_PADRE = ?\n" +
                    "  AND M.ESTATUS = 'S'\n" +
                    "  AND MR.ESTATUS = 'S'\n" +
                    "  AND R.ESTATUS = 'S'\n" +
                    "  AND U.ESTATUS = 'S'\n" +
                    "  AND M.ID_MENU != M.ID_PADRE\n" +
                    "  AND M.ID_MENU = MR.ID_MENU\n" +
                    "  AND MR.ID_ROL = R.ID_ROL\n" +
                    "  AND R.ID_ROL = 3\n" +
                    "  AND U.MATRICULA = ?";
}
