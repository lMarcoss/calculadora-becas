package edu.calc.becas.malumnos.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.*;
import static edu.calc.becas.malumnos.dao.QueriesAlumnos.*;


@Repository
@Slf4j
public class AlumnosDaoImpl extends BaseDao implements AlumnosDao {


    public AlumnosDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return null;
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String idActividad) {
        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        boolean byActivity = !idActividad.equalsIgnoreCase(DEFAULT_ESTATUS);

        String queryGetALl = addConditionFilterByStatus(status, QRY_GET_ALL, QRY_CONDITION_ESTATUS);

        if (byActivity) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_ACTIVIDAD.replace("?", "'" + idActividad + "'"));
        }


        queryGetALl = queryGetALl.concat(QRY_ORDER_BY_NOMBRE_ALUMNO);

        queryGetALl = addQueryPageable(page, pageSize, queryGetALl);

        int lengthDataTable = this.jdbcTemplate.queryForObject(createQueryCountItem(queryGetALl), Integer.class);

        List<Alumno> data = this.jdbcTemplate.query(queryGetALl, (rs, rowNum) -> mapperAlumno(rs));

        if (!pageable) {
            page = 0;
            pageSize = lengthDataTable;
        }
        return new WrapperData(data, page, pageSize, lengthDataTable);

    }

    @Override
    public Alumno add(Alumno object) {
        try {
            int idAlumno = this.jdbcTemplate.queryForObject(QRY_ID_ALUMNO, Integer.class);
            int valueAlumno = this.jdbcTemplate.queryForObject(QRY_EXISTE_ALUMNO, new Object[]{object.getMatricula()}, Integer.class);
            if (valueAlumno == 0) {
                this.jdbcTemplate.update(QRY_ADD, createObject(idAlumno, object));
            }

            int valueAlumnoPeriodo = this.jdbcTemplate.queryForObject(QRY_EXISTE_ALUMNO_PERIODO,
                    new Object[]{
                            object.getCicloEscolar(),
                            object.getMatricula()}, Integer.class);
            if (valueAlumnoPeriodo == 0) {
                this.jdbcTemplate.update(QRY_ADD_ALUMNO_PERIODO,
                        object.getMatricula(),
                        object.getGrupo(),
                        object.getGrupo(),
                        object.getLicenciatura(),
                        object.getLicenciatura(),
                        object.getCicloEscolar(),
                        object.getCicloEscolar());
            }

            int valueAlumnoActividad = this.jdbcTemplate.queryForObject(QRY_EXISTE_ALUMNO_ACTIVIDAD,
                    new Object[]{
                            object.getMatricula()}, Integer.class);

            if (valueAlumnoActividad == 0) {
                this.jdbcTemplate.update(QRY_ADD_ALUMNO_ACTIVIDAD, object.getIdDetalleActividad(),
                        object.getMatricula());
            } else {
                this.jdbcTemplate.update(QRY_UPD_ALUMNO_ACTIVIDAD, object.getIdDetalleActividad(),
                        object.getMatricula());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public Usuario getUserInfo(String matricula) {
        String query = "SELECT ID_ALUMNO,ALM.NOMBRES,ALM.APE_PATERNO,ALM.APE_MATERNO, '3' ID_ROL, 'ALUMNO' ROL, ALM.MATRICULA as USERNAME, CURP COMMONVAL01, AL.CVE_GRUPO COMMONVAL02,\n" +
                "    AL.DESC_LICENCIATURA COMMONVAL03,\n" +
                "             AT.NOMBRE_ACTIVIDAD COMMONVAL04,\n" +
                "             Concat ( AC.HORA,':',AC.AM_PM) COMMONVAL05,\n" +
                "       ALM.CODIGO_RFID COMMONVAL06\n" +
                "      FROM ALUMNOS ALM, HORARIO_ACTIVIDAD AC, ACTIVIDAD_ALUMNO CAL, ALUMNOS_DAT_PERIODO AL, ACTIVIDADES AT\n" +
                "      WHERE\n" +
                "      AC.ID_HORARIO_ACTIVIDAD = CAL.ID_HORARIO_ACTIVIDAD\n" +
                "      AND CAL.ID_ALUMNO_P = AL.ID_ALUMNOP\n" +
                "      AND AC.ID_ACTIVIDAD = AT.ID_ACTIVIDAD\n" +
                "      AND ALM.MATRICULA = AL.MATRICULA\n" +
                "AND ALM.MATRICULA = ? ORDER BY AL.ID_ALUMNOP";
        Usuario us = new Usuario();
        List<Usuario> usr = this.jdbcTemplate.query(query, new Object[]{matricula}, (((rs, i) -> mapperAlumnoLogin(rs))));

        if (usr == null || usr.size() == 0) {
            String queryTmp = "SELECT ID_ALUMNO,NOMBRES, APE_PATERNO, APE_MATERNO, '3' ID_ROL, 'ALUMNO' ROL, AC.MATRICULA as USERNAME, CURP COMMONVAL01, AC.CVE_GRUPO COMMONVAL02,\n" +
                    "       AC.DESC_LICENCIATURA COMMONVAL03, '' COMMONVAL04, '' COMMONVAL05, AL.CODIGO_RFID COMMONVAL06 FROM ALUMNOS AL, ALUMNOS_DAT_PERIODO AC\n" +
                    "    WHERE AL.MATRICULA = AC.MATRICULA AND AC.MATRICULA = ? and AL.ESTATUS = 'S'";
            return this.jdbcTemplate.queryForObject(queryTmp, new Object[]{matricula}, (((rs, i) -> mapperAlumnoLogin(rs))));
        } else {
            return usr.get(0);
        }

    }

    private Usuario mapperAlumnoLogin(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        usuario.setIdUsuario(rs.getInt("ID_ALUMNO"));
        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setApePaterno(rs.getString("APE_PATERNO"));
        usuario.setApeMaterno(rs.getString("APE_MATERNO"));
        usuario.setUsername(rs.getString("USERNAME"));
        usuario.setCommonVal01(rs.getString("COMMONVAL01"));
        usuario.setCommonVal02(rs.getString("COMMONVAL02"));
        usuario.setCommonVal03(rs.getString("COMMONVAL03"));
        usuario.setCommonVal04(rs.getString("COMMONVAL04"));
        usuario.setCommonVal05(rs.getString("COMMONVAL05"));
        usuario.setCommonVal06(rs.getString("COMMONVAL06"));
        rol.setIdRol(rs.getInt("ID_ROL"));
        rol.setNombre(rs.getString("ROL"));
        usuario.setRol(rol);
        return usuario;
    }

    private Object[] createObject(int idAlumno, Alumno detalle) {
        return new Object[]{
                idAlumno,
                detalle.getCurp(),
                detalle.getMatricula(),
                detalle.getNombres(),
                detalle.getApePaterno(),
                detalle.getApeMaterno(),
                "S",
                "ADMIN"
        };
    }

    @Override
    public Alumno update(Alumno alumno) {
        int i = this.jdbcTemplate
                .update(QRY_UPDATE_ALUMNO_PERIODO,
                        alumno.getMatricula(),
                        alumno.getGrupo(),
                        alumno.getDsGrupo(),
                        alumno.getIdLicenciatura(),
                        alumno.getLicenciatura(),
                        alumno.getEstatus(),
                        alumno.getIdAlumnoPeriodo()
                );

        return alumno;
    }

    private Alumno mapperAlumno(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno(rs.getString("ESTATUS"));
        ActividadVo actividadVo = new ActividadVo();
        alumno.setIdAlumno(rs.getString("ID_ALUMNO"));
        alumno.setMatricula(rs.getString("MATRICULA"));
        alumno.setNombres(rs.getString("NOMBRES"));
        alumno.setApePaterno(rs.getString("APE_PATERNO"));
        alumno.setApeMaterno(rs.getString("APE_MATERNO"));
        actividadVo.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        actividadVo.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
        alumno.setActividad(actividadVo);
        return alumno;
    }

    private Alumno mapperAlumnoCargas(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno(rs.getString("ESTATUS"));
        alumno.setIdAlumno(rs.getString("ID_ALUMNO"));
        alumno.setIdAlumnoPeriodo(rs.getLong("ID_ALUMNOP"));
        alumno.setMatricula(rs.getString("MATRICULA"));
        alumno.setCurp(rs.getString("CURP"));
        alumno.setNombres(rs.getString("NOMBRES"));
        alumno.setApePaterno(rs.getString("APE_PATERNO"));
        alumno.setApeMaterno(rs.getString("APE_MATERNO"));
        alumno.setGrupo(rs.getString("CVE_GRUPO"));

        alumno.setLicenciatura(rs.getString("DESC_LICENCIATURA"));
        alumno.setCodigoRFid(rs.getString("CODIGO_RFID"));
        alumno.setIdLicenciatura(rs.getString("CVE_LICENCIATURA"));
        alumno.setDsGrupo(rs.getString("DESC_GRUPO"));

        return alumno;
    }

    @Override
    public WrapperData<Alumno> getAllByStatusLoad(int page, int pageSize, String status, String periodo, String licenciatura, String grupo) {
        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        String queryGetALl = "";

        queryGetALl = queryGetALl.concat(QRY_ALUMNOS_CARGAS);

        //queryGetALl.concat(QRY_CONDITION_ACTIVIDAD.replace("?", "'" + idActividad + "'"));
        if (!licenciatura.equals(ALL_ITEMS)) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_LIC.replace("?", "'" + licenciatura + "'"));
        }

        if (!grupo.equals(ALL_ITEMS)) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_GRUPO.replace("?", "'" + grupo + "'"));
        }


        int lengthDataTable = this.jdbcTemplate.queryForObject(createQueryCountItem(QRY_ALUMNOS_CARGAS), new Object[]{periodo}, Integer.class);

        List<Alumno> data = this.jdbcTemplate.query(queryGetALl, new Object[]{periodo}, (rs, rowNum) -> mapperAlumnoCargas(rs));

        if (!pageable) {
            page = 0;
            pageSize = lengthDataTable;
        }
        return new WrapperData<>(data, page, pageSize, lengthDataTable);
    }


}
