package edu.calc.becas.malumnos.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.DEFAULT_ESTATUS;
import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.malumnos.dao.QueriesAlumnos.*;


@Repository
public class AlumnosDaoImpl extends BaseDao implements AlumnosDao {


    public AlumnosDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
        try{
            int idAlumno = this.jdbcTemplate.queryForObject(QRY_ID_ALUMNO, Integer.class);
            int valueAlumno = this.jdbcTemplate.queryForObject(QRY_EXISTE_ALUMNO, new Object[]{object.getMatricula()}, Integer.class);
            if (valueAlumno == 0){
              this.jdbcTemplate.update(QRY_ADD, createObject(idAlumno, object));
            }

          int valueAlumnoPeriodo = this.jdbcTemplate.queryForObject(QRY_EXISTE_ALUMNO_PERIODO,
                                          new Object[]{
                                            object.getCicloEscolar(),
                                            object.getMatricula()}, Integer.class);
          if (valueAlumnoPeriodo == 0){
            this.jdbcTemplate.update(QRY_ADD_ALUMNO_PERIODO,
              new Object[]{
                object.getMatricula(),
                object.getGrupo(),
                object.getGrupo(),
                object.getLicenciatura(),
                object.getLicenciatura(),
                object.getCicloEscolar(),
                object.getCicloEscolar()
              });
          }
//INSERT INTO ALUMNOS_DAT_PERIODO (MATRICULA,CVE_GRUPO, DESC_GRUPO, CVE_LICENCIATURA, DESC_LICENCIATURA, CVE_PERIODO, DESC_PERIDODO) VALUES

            this.jdbcTemplate.update(QRY_ADD_ALUMNO_ACTIVIDAD, new Object[]{
                    object.getIdDetalleActividad(),
                    object.getMatricula()
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
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
    public Alumno update(Alumno object) {
        return null;
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
    ActividadVo actividadVo = new ActividadVo();
    alumno.setIdAlumno(rs.getString("ID_ALUMNO"));
    alumno.setMatricula(rs.getString("MATRICULA"));
    alumno.setCurp(rs.getString("CURP"));
    alumno.setNombres(rs.getString("NOMBRES"));
    alumno.setApePaterno(rs.getString("APE_PATERNO"));
    alumno.setApeMaterno(rs.getString("APE_MATERNO"));
    alumno.setGrupo(rs.getString("CVE_GRUPO"));
    return alumno;
  }

  @Override
  public WrapperData getAllByStatusLoad(int page, int pageSize, String status, String param1) {
    boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);

    String queryGetALl = "";

    queryGetALl = queryGetALl.concat(QRY_ALUMNOS_CARGAS);

    int lengthDataTable = this.jdbcTemplate.queryForObject(createQueryCountItem(QRY_ALUMNOS_CARGAS), new Object[]{param1}, Integer.class);

    List<Alumno> data = this.jdbcTemplate.query(queryGetALl, new Object[]{param1}, (rs, rowNum) -> mapperAlumnoCargas(rs));

    if (!pageable) {
      page = 0;
      pageSize = lengthDataTable;
    }
    return new WrapperData(data, page, pageSize, lengthDataTable);
  }
}
