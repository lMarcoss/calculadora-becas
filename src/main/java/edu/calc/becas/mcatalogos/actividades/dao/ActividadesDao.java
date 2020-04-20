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

public interface ActividadesDao extends CrudGenericDao<ActividadVo> {
    WrapperData getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, Usuario username);

    List<LabelValueData> getActividades();

    ActividadVo update(ActividadVo detalle);

    DetalleActividadVo add(DetalleActividadVo detalle) throws GenericException;

    DetalleActividadVo updateDetail(DetalleActividadVo detalle) throws GenericException;

    WrapperData<ActividadVo> getAllByStatusAndTipoActividadHorario(int page, int pageSize, String status, String param1, String swHorario);

    int persistencePorcentaje(List<ReportPercentActivity> alumnos, Parcial parcialActual, CicloEscolarVo cicloEscolarActual);
}

