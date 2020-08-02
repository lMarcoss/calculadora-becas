package edu.calc.becas.common.base.dao;


import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static edu.calc.becas.common.base.dao.QueriesBaseDao.QRY_COUNT_ITEM;
import static edu.calc.becas.common.base.dao.QueriesBaseDao.QRY_PAGEABLE;
import static edu.calc.becas.common.utils.Constant.DEFAULT_ESTATUS;
import static edu.calc.becas.common.utils.Constant.ITEMS_FOR_PAGE;

/**
 * Metodos comunes para consulta a la BD
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 3/26/19
 */
@Component
public class BaseDao {
    protected final JdbcTemplate jdbcTemplate;
    protected final MessageApplicationProperty messageApplicationProperty;

    /**
     * @param jdbcTemplate
     * @param messageApplicationProperty
     */
    public BaseDao(JdbcTemplate jdbcTemplate, MessageApplicationProperty messageApplicationProperty) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageApplicationProperty = messageApplicationProperty;
    }

    /**
     * Agrega query de paginacion a la query base de consulta
     *
     * @param page        pagina
     * @param pageSize    registros por pagina
     * @param queryGetALl query base
     * @return query paginado
     */
    protected String addQueryPageable(int page, int pageSize, String queryGetALl) {

        boolean pageable = pageSize != Integer.parseInt(ITEMS_FOR_PAGE);
        if (pageable) {
            queryGetALl = queryGetALl.concat(getQueryPageable(page, pageSize));
        }

        return queryGetALl;
    }

    /**
     * Crea query de paginacion para consultas
     *
     * @param page     pagina
     * @param pageSize registros por pagina
     * @return query para consultas paginadas
     */
    private String getQueryPageable(int page, int pageSize) {
        return String.format(QRY_PAGEABLE, pageSize, (page * pageSize));
    }

    /**
     * Agrega condicion para recuperar registros por estatus
     *
     * @param status             estatus
     * @param queryBase          queryBase
     * @param qryConditionStatus query con condicion estatus
     * @return query para obtener registros por estatus
     */
    protected String addConditionFilterByStatus(String status, String queryBase, String qryConditionStatus) {
        boolean byStatus = !status.equalsIgnoreCase(DEFAULT_ESTATUS);
        if (byStatus) {
            queryBase = queryBase.concat(qryConditionStatus.replace("?", "'" + status + "'"));
        }
        return queryBase;
    }

    /**
     * Crea una query para contar elementos en una consulta
     *
     * @param queryGetALl queryBase
     * @return query para contar registros
     */
    protected String createQueryCountItem(String queryGetALl) {
        int startQryPageable = queryGetALl.indexOf("LIMIT");
        if (startQryPageable >= 0) {
            return String.format(QRY_COUNT_ITEM, queryGetALl.substring(0, startQryPageable));
        } else {
            return String.format(QRY_COUNT_ITEM, queryGetALl);
        }

    }
}
