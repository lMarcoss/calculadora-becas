package edu.calc.becas.mcatalogos.actividades.dao;

import edu.calc.becas.common.base.dao.CrudGenericDao;
import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;

import java.util.List;

/**
 * Define las operaciones a la BD para administracion de actividades
 */
public interface ActividadesDao extends CrudGenericDao<ActividadVo> {
    /**
     * Obtiene los horarios de las actiidades por filtro
     *
     * @param page        pagina
     * @param pageSize    registros por pagina
     * @param idActividad actividad
     * @param ciclo       periodo
     * @param status      estatus
     * @param username    usuario
     * @return horarios
     */
    WrapperData<DetalleActividadVo> getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, Usuario username);

    /**
     * recuperar todas las actividades por clave-valor
     *
     * @return
     */
    List<LabelValueData> getActividades();

    /**
     * Actualiza los datos de una actividad
     *
     * @param actividadVo
     * @return
     */
    ActividadVo update(ActividadVo actividadVo);

    /**
     * Registra los datos de un horario de actividad
     *
     * @param detalle horario
     * @return horario
     * @throws GenericException error al registro
     */
    DetalleActividadVo add(DetalleActividadVo detalle) throws GenericException;

    /**
     * Actualiza los datos de un horario de actividad
     *
     * @param detalle
     * @return
     * @throws GenericException
     */
    DetalleActividadVo updateDetail(DetalleActividadVo detalle) throws GenericException;

    /**
     * Obtiene la lista de todas las actividades por filtro
     *
     * @param page      pagina
     * @param pageSize  registros por pagina
     * @param status    estatus
     * @param param1    parametro de filtro
     * @param swHorario horario
     * @return
     */
    WrapperData<ActividadVo> getAllByStatusAndTipoActividadHorario(int page, int pageSize, String status, String param1, String swHorario);

    /**
     * Registra % de asistencia de los alumnos a una actividad
     *
     * @param alumnos            lista de alumnos y sus % de asistencias
     * @param parcialActual      parcial
     * @param cicloEscolarActual periodo
     * @return
     */
    int persistencePorcentaje(List<ReportPercentActivity> alumnos, Parcial parcialActual, CicloEscolarVo cicloEscolarActual);
}

