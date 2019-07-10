package edu.calc.becas.mcatalogos.actividades.service;

import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.mcatalogos.actividades.dao.ActividadesDao;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadesServiceImpl implements ActividadesService{

    private final ActividadesDao actividadesDao;

    @Autowired
    public ActividadesServiceImpl(ActividadesDao actividadesDao){this.actividadesDao = actividadesDao;}

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status)
    {
        return actividadesDao.getAllByStatus(page,pageSize, status);
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return null;
    }

    @Override
    public ActividadVo add(ActividadVo actividad) {
        return actividadesDao.add(actividad);
    }

    @Override
    public ActividadVo update(ActividadVo actividadVo) {
        return null;
    }

    @Override
    public WrapperData getAllDetalle(int page, int pageSize, String idActividad, String ciclo) {
        return actividadesDao.getAllDetalle(page, pageSize, idActividad, ciclo);
    }

    @Override
    public List<LabelValueData> getActividades() {
        return actividadesDao.getActividades();
    }

    @Override
    public DetalleActividadVo udateActividad(DetalleActividadVo detalle) {
        return null;
    }

    @Override
    public DetalleActividadVo add(DetalleActividadVo detalle) {
        return actividadesDao.add(detalle);
    }

    @Override
    public DetalleActividadVo udateDetail(DetalleActividadVo detalle) {
        return actividadesDao.udateDetail(detalle);
    }




}
