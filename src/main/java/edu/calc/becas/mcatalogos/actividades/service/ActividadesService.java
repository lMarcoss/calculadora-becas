package edu.calc.becas.mcatalogos.actividades.service;

import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.common.service.CrudGenericService;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;

import java.util.List;

public interface ActividadesService extends CrudGenericService<ActividadVo> {

    WrapperData getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, String username);

    List<LabelValueData> getActividades();

    ActividadVo update(ActividadVo detalle);

    DetalleActividadVo add(DetalleActividadVo detalle);

    DetalleActividadVo udateDetail(DetalleActividadVo detalle);
}
