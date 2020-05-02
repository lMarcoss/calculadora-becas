package edu.calc.becas.mcatalogos.actividades.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.actividades.dao.AlumnoActividadDao;
import edu.calc.becas.malumnos.model.AlumnoActividad;
import edu.calc.becas.mcarga.hrs.blibioteca.model.Hora;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import edu.calc.becas.mreporte.actividades.percent.activity.dao.ReportPercentActivitiesDao;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.ALL_ITEMS;
import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.mcatalogos.actividades.dao.QueriesActividades.*;

@Repository
public class ActividadesDaoImpl extends BaseDao implements ActividadesDao {
    private static final Logger LOG = LoggerFactory.getLogger(ActividadesDaoImpl.class);

    private final ReportPercentActivitiesDao reportPercentActivitiesDao;
    private final AlumnoActividadDao alumnoActividadDao;

    public ActividadesDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty,
                              ReportPercentActivitiesDao reportPercentActivitiesDao,
                              AlumnoActividadDao alumnoActividadDao) {
        super(jdbcTemplate, messageApplicationProperty);
        this.reportPercentActivitiesDao = reportPercentActivitiesDao;
        this.alumnoActividadDao = alumnoActividadDao;
    }


    @Override
    public WrapperData<ActividadVo> getAllByStatus(int page, int pageSize, String status) {

        return getAllByAllParam(page, pageSize, status, ALL_ITEMS, ALL_ITEMS);
    }

    private WrapperData<ActividadVo> getAllByAllParam(int page, int pageSize, String status, String tipoActividad, String swHorario) {
        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        boolean byTipoActividad = !tipoActividad.equalsIgnoreCase(ALL_ITEMS);
        boolean bySwHorario = !swHorario.equalsIgnoreCase(ALL_ITEMS);

        String queryGetALl = addConditionFilterByStatus(status, QRY_ACTIVIDADES, QRY_CONDITION_ESTATUS_ACTIVIDADES);

        if (byTipoActividad) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_TIPO_ACTIVIDAD.replace("?", "'" + tipoActividad + "'"));
        }

        if (bySwHorario) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_TIPO_SW_HORARIO.replace("?", "'" + swHorario + "'"));
        }

        queryGetALl = addQueryPageable(page, pageSize, queryGetALl);

        int lengthDatable = this.jdbcTemplate.queryForObject(createQueryCountItem(queryGetALl), Integer.class);
        List<ActividadVo> data = this.jdbcTemplate.query(queryGetALl, (rs, rowNum) -> mapperActividades(rs));

        if (!pageable) {
            page = 0;
            pageSize = lengthDatable;
        }

        return new WrapperData(data, page, pageSize, lengthDatable);
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return getAllByAllParam(page, pageSize, status, param1, ALL_ITEMS);
    }


    @Override
    public WrapperData getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, Usuario usuario) {

        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        boolean byActividad = !idActividad.equalsIgnoreCase(ALL_ITEMS);
        boolean byUser = !usuario.getUsername().equalsIgnoreCase(ALL_ITEMS);


        String queryGetALl = addConditionFilterByStatus(status, QRY_DETALLE_ACTIVIDADES, QRY_CONDITION_ESTATUS_HORARIO_ACTIVIDADES);
        String queryCountItem = QRY_COUNT_DETALLE_ACTIVIDADES;

        if (byActividad) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_ID_ACTIVIDAD.replace("?", "'" + idActividad + "'"));
            queryCountItem = queryCountItem.concat(QRY_CONDITION_ID_ACTIVIDAD.replace("?", "'" + idActividad + "'"));
        }

        if (byUser) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_ID_USUARIO.replace("?", "'" + usuario.getIdUsuario() + "'"));
            queryCountItem = queryCountItem.concat(QRY_CONDITION_ID_USUARIO.replace("?", "'" + usuario.getIdUsuario() + "'"));
        }

        queryGetALl = queryGetALl.concat(QRY_ORDER_BY);
        queryGetALl = addQueryPageable(page, pageSize, queryGetALl);

        int lengthDatable = this.jdbcTemplate.queryForObject(queryCountItem, Integer.class);

        List<DetalleActividadVo> data = this.jdbcTemplate.query(queryGetALl, (rs, rowNum) -> mapperDetalleActividades(rs));

        if (!pageable) {
            page = 0;
            pageSize = lengthDatable;
        }
        return new WrapperData(data, page, pageSize, lengthDatable);
    }


    @Override
    public List<LabelValueData> getActividades() {
        return this.jdbcTemplate.query(QRY_LIST_ACTIVIDAD, (rs, rowNum) -> LabelValueData.mapperLavelValue(rs));
    }


    @Override
    public ActividadVo add(ActividadVo actividad) {
        this.jdbcTemplate.update(QRY_ADD, createObjectParamUpdate(actividad));
        return actividad;
    }

    @Override
    public ActividadVo update(ActividadVo object) {
        this.jdbcTemplate.update(QRY_UPDATE_ACTIVIDAD,
                new Object[]{
                        object.getNombreActividad(),
                        object.getEstatus(),
                        object.getIdActividad()});
        return object;
    }


    @Override
    public DetalleActividadVo add(DetalleActividadVo detalle) throws GenericException {
        try {
            this.jdbcTemplate.update(QRY_ADD_HORA_ACTIVIDAD, createObjectParamDetalle(detalle));
            return detalle;
        } catch (DuplicateKeyException e) {
            throw new GenericException(messageApplicationProperty.getErrorAsignarUsuariosAHorarioActividad());
        } catch (Exception e) {
            throw new GenericException(e);
        }

    }

    @Override
    public DetalleActividadVo updateDetail(DetalleActividadVo detalle) throws GenericException {
        try {
            this.jdbcTemplate.update(QRY_UPDATE_HORA_ACTIVIDAD, detalle.getHorario().getNumHora(),
                    detalle.getHorario().getAmPm(),
                    detalle.getNumeroAlumnos(),
                    detalle.getUsuario().getIdUsuario(),
                    detalle.getEstatus(),
                    detalle.getComentario(),
                    detalle.getIdDetalleActividad());

            return detalle;
        } catch (DuplicateKeyException e) {
            throw new GenericException(messageApplicationProperty.getErrorAsignarUsuariosAHorarioActividad());
        } catch (Exception e) {
            throw new GenericException(e);
        }

    }

    @Override
    public WrapperData<ActividadVo> getAllByStatusAndTipoActividadHorario(int page, int pageSize, String status, String param1, String swHorario) {
        return getAllByAllParam(page, pageSize, status, param1, swHorario);
    }

    private ActividadVo mapperActividades(ResultSet rs) throws SQLException {
        ActividadVo actividadVo = new ActividadVo(rs.getString("ESTATUS"));
        actividadVo.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        actividadVo.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
        actividadVo.setObligatorio(rs.getString("OBLIGATORIO"));
        return actividadVo;
    }

    private DetalleActividadVo mapperDetalleActividades(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        Hora hora = new Hora();
        DetalleActividadVo detalle = new DetalleActividadVo(rs.getString("ESTATUS"));

        detalle.setIdActividad(rs.getInt("ID_ACTIVIDAD"));
        detalle.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));

        detalle.setIdDetalleActividad(rs.getInt("ID_HORARIO_ACTIVIDAD"));
        hora.setIdHora("");
        hora.setNumHora(rs.getInt("HORA"));
        hora.setAmPm(rs.getString("AM_PM"));

        detalle.setNumeroAlumnos(rs.getInt("NUMERO_ALUMNOS"));
        detalle.setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
        //detalle.setComentario(rs.getString("COMENTARIO"));

        detalle.setIdCicloEscolar(rs.getString("CVE_PERIODO"));
        detalle.setCicloEscolar(rs.getString("DESC_PERIDODO"));
        detalle.setPeriodo(new CicloEscolarVo());
        List<AlumnoActividad> alumnos = new ArrayList<AlumnoActividad>();
      alumnos.add(new AlumnoActividad());
        detalle.setAlumnos(alumnos);
        usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setApePaterno(rs.getString("APE_PATERNO"));
        usuario.setApeMaterno(rs.getString("APE_MATERNO"));

        detalle.setUsuario(usuario);
        detalle.setHorario(hora);
        return detalle;
    }

    private Object[] createObjectParamUpdate(ActividadVo actividad) {
        return new Object[]{actividad.getNombreActividad(), actividad.getEstatus(), actividad.getAgregadoPor()};
    }

    //199E2A1EswF5CVrh
    private Object[] createObjectParamDetalle(DetalleActividadVo detalle) {
        return new Object[]{
                detalle.getIdActividad(),
                detalle.getHorario().getNumHora(),
                detalle.getHorario().getAmPm(),
                detalle.getNumeroAlumnos(),
                detalle.getUsuario().getIdUsuario(),
                detalle.getIdCicloEscolar(),
                detalle.getCicloEscolar(),
                detalle.getEstatus(),
                detalle.getAgregadoPor(),
                detalle.getComentario()
        };
    }


    @Override
    public int persistencePorcentaje(List<ReportPercentActivity> alumnos, Parcial parcialActual, CicloEscolarVo cicloEscolarActual) {
        int count = 0;
        for (ReportPercentActivity alumno : alumnos) {
            try {

                // obtiene la actividad del alumno
                ActividadVo actividadVo = alumnoActividadDao.getActividadByAlumno(alumno.getMatricula(), cicloEscolarActual);
                UserLogin userLogin = new UserLogin();
                userLogin.setUsername("ADMIN");
                reportPercentActivitiesDao.addPercentActivity(BigDecimal.valueOf(alumno.getPorcentajeActividad()), actividadVo.getIdActividad(), userLogin,parcialActual);


                count++;
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }
        return count;
    }

}
