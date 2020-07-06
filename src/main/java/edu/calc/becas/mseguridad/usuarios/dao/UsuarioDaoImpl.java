package edu.calc.becas.mseguridad.usuarios.dao;

import edu.calc.becas.common.base.dao.BaseDao;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mseguridad.rolesypermisos.model.Rol;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mvc.config.MessageApplicationProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;
import static edu.calc.becas.common.utils.Constant.TIPO_USUARIO_DEFAULT;
import static edu.calc.becas.mseguridad.usuarios.dao.QueriesUsuario.*;

/**
 * Repositorio para administracion de usuarios en la Base de Datos
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 4/14/19
 */
@Repository
@Slf4j
public class UsuarioDaoImpl extends BaseDao implements UsuarioDao {

    @Value("${prop.security.secret.key.start}")
    private String secretKeyStart;

    @Value("${prop.security.secret.key.end}")
    private String secretKeyEnd;

    public UsuarioDaoImpl(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        super(jdbcTemplate, messageApplicationProperty);
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status) {
        return null;
    }

    /**
     * Regresa la lista de usuarios de la Base de Datos de forma paginada
     *
     * @param page
     * @param pageSize
     * @param status
     * @param tipoUsuario
     * @return lista de usuarios
     */
    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String tipoUsuario) {

        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        boolean byTipoUsuario = !tipoUsuario.equalsIgnoreCase(TIPO_USUARIO_DEFAULT);

        String queryGetALl = addConditionFilterByStatus(status, QRY_GET_ALL, QRY_CONDITION_ESTATUS);

        if (byTipoUsuario) {
            queryGetALl = queryGetALl.concat(QRY_CONDITION_TIPO_USUARIO.replace("?", "'" + tipoUsuario + "'"));
        }

        queryGetALl = queryGetALl.concat(QRY_ORDER_BY);

        queryGetALl = addQueryPageable(page, pageSize, queryGetALl);

        int lengthDataTable = this.jdbcTemplate.queryForObject(createQueryCountItem(queryGetALl), Integer.class);

        List<Usuario> data = this.jdbcTemplate.query(queryGetALl, (rs, rowNum) -> mapperUsuario(rs));

        if (!pageable) {
            page = 0;
            pageSize = lengthDataTable;
        }
        return new WrapperData(data, page, pageSize, lengthDataTable);
    }

    /**
     * Inserta un usuario a la Base de Datos
     *
     * @param usuario
     * @return lista de usuarios
     * @throws GenericException
     */
    @Override
    public Usuario add(Usuario usuario) throws GenericException {
        try {
            this.jdbcTemplate.update(QRY_ADD,
                    usuario.getNombres().trim(), usuario.getApePaterno().trim(), usuario.getApeMaterno().trim(),
                    usuario.getRol().getIdRol(), usuario.getUsername().trim(),
                    secretKeyStart, usuario.getPassword(), secretKeyEnd,
                    usuario.getDiasRetrocesoReporte(), usuario.getEstatus().trim(), usuario.getAgregadoPor().trim());
            return usuario;
        } catch (DuplicateKeyException d) {
            log.error(ExceptionUtils.getStackTrace(d));
            throw new GenericException("Usuario duplicado con el correo " + usuario.getUsername());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GenericException(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Actualiza los datos de un usuario en la BD
     *
     * @param usuario
     * @return usuario
     */
    @Override
    public Usuario update(Usuario usuario) {
        String password = usuario.getPassword();
        if (password != null && !password.equalsIgnoreCase("")) {
            this.jdbcTemplate.update(QRY_UPDATE_WITH_PASSWORD, usuario.getNombres().trim(), usuario.getApePaterno().trim(),
                    usuario.getApeMaterno().trim(), usuario.getRol().getIdRol(), usuario.getUsername().trim(),
                    secretKeyStart, usuario.getPassword(), secretKeyEnd, usuario.getDiasRetrocesoReporte(),
                    usuario.getEstatus().trim(), usuario.getActualizadoPor().trim(), usuario.getIdUsuario());
        } else {
            this.jdbcTemplate.update(QRY_UPDATE, usuario.getNombres().trim(), usuario.getApePaterno().trim(),
                    usuario.getApeMaterno().trim(), usuario.getRol().getIdRol(), usuario.getUsername().trim(),
                    usuario.getDiasRetrocesoReporte(), usuario.getEstatus().trim(), usuario.getActualizadoPor().trim(),
                    usuario.getIdUsuario());
        }

        return usuario;
    }

    /**
     * Mapea los datos de usuario a objeto
     *
     * @param rs usuario recuperado en BD
     * @return usuario
     * @throws SQLException
     */
    private Usuario mapperUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(rs.getString("ESTATUS"));
        usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setApePaterno(rs.getString("APE_PATERNO"));
        usuario.setApeMaterno(rs.getString("APE_MATERNO"));
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("ID_ROL"));
        rol.setNombre(rs.getString("TIPO_USUARIO"));
        usuario.setRol(rol);
        usuario.setUsername(rs.getString("USERNAME"));
        usuario.setDiasRetrocesoReporte(rs.getInt("DIAS_RETROCESO_REPORTE"));
        return usuario;
    }

    /**
     * Consulta un usuario por username
     *
     * @param username usuario a recuperar
     * @return usuario recuperado/encontrado
     */
    @Override
    public Usuario getByUsername(String username) {
        return this.jdbcTemplate.queryForObject(QRY_GET_ALL.concat(QRY_CONDITION_BY_USERNAME), new Object[]{username}, ((rs, i) -> mapperUsuario(rs)));
    }
}
