package edu.calc.becas.mcatalogos.actividades.service;

import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mseguridad.login.model.UserLogin;

import java.util.List;

/**
 * Operaciones para administracion de talleres o actividades con la BD
 */
public interface ActividadesService extends CrudGenericService<ActividadVo> {

    /**
     * Obtiene los horarios de las actividades
     *
     * @param page        pagina
     * @param pageSize    registros por pagina
     * @param idActividad actividad
     * @param ciclo       periodo
     * @param status      estatus del registro
     * @param username    usuario
     * @param userLogin   usuario
     * @return horarios de actividades
     */
    WrapperData<DetalleActividadVo> getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, String username, UserLogin userLogin);

    /**
     * Genera una lista de clave-valor de las actividades
     *
     * @return lista de clave-valos de actividades
     */
    List<LabelValueData> getActividades();

    /**
     * Actualiza los datos de una actividad
     *
     * @param detalle datos de la actividad
     * @return actividad
     */
    ActividadVo update(ActividadVo detalle);

    /**
     * Registra un horario de una actividad
     *
     * @param detalle datos del horario
     * @return horario
     * @throws GenericException error de registro
     */
    DetalleActividadVo add(DetalleActividadVo detalle) throws GenericException;

    /**
     * Actualiza los datos de un horario de una actividad
     *
     * @param detalle datos del horario
     * @return horario
     * @throws GenericException erro de actualizacion
     */
    DetalleActividadVo updateDetail(DetalleActividadVo detalle) throws GenericException;

    /**
     * recupera todas las actividades por filtro
     *
     * @param page          pagina
     * @param pageSize      registros por pagina
     * @param status        estatus de los registros
     * @param tipoActividad tipo actividad
     * @param swHorario     horarrio
     * @return
     */
    WrapperData<ActividadVo> getAllByStatusAndTipoActividadHorario(int page, int pageSize, String status, String tipoActividad, String swHorario);
}
