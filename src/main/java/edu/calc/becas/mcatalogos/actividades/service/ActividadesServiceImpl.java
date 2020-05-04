package edu.calc.becas.mcatalogos.actividades.service;

import edu.calc.becas.common.model.LabelValueData;
import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mcatalogos.actividades.dao.ActividadesDao;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.actividades.model.DetalleActividadVo;
import edu.calc.becas.mseguridad.usuarios.model.Usuario;
import edu.calc.becas.mseguridad.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.calc.becas.common.utils.Constant.ALL_ITEMS;

@Service
public class ActividadesServiceImpl implements ActividadesService{

    private final ActividadesDao actividadesDao;
    private final UsuarioService usuarioService;

    @Autowired
    public ActividadesServiceImpl(ActividadesDao actividadesDao, UsuarioService usuarioService){this.actividadesDao = actividadesDao;
        this.usuarioService = usuarioService;
    }

    @Override
    public WrapperData getAllByStatus(int page, int pageSize, String status)
    {
        return actividadesDao.getAllByStatus(page,pageSize, status);
    }

    @Override
    public WrapperData getAllByStatusAndOneParam(int page, int pageSize, String status, String param1) {
        return actividadesDao.getAllByStatusAndOneParam(page, pageSize, status, param1);
    }

    @Override
    public ActividadVo add(ActividadVo actividad) throws GenericException {
        return actividadesDao.add(actividad);
    }

    @Override
    public WrapperData<DetalleActividadVo> getAllDetalle(int page, int pageSize, String idActividad, String ciclo, String status, String username) {
        Usuario usuario;
        if(username.equalsIgnoreCase(ALL_ITEMS)){
            usuario = new Usuario();
            usuario.setUsername(ALL_ITEMS);
        }else {
            usuario = usuarioService.getByUsername(username);
        }
        return actividadesDao.getAllDetalle(page, pageSize, idActividad, ciclo, status, usuario);
    }

    @Override
    public List<LabelValueData> getActividades() {
        return actividadesDao.getActividades();
    }

    @Override
    public ActividadVo update(ActividadVo detalle) {
        return actividadesDao.update(detalle);
    }

    @Override
    public DetalleActividadVo add(DetalleActividadVo detalle) throws GenericException {
        return actividadesDao.add(detalle);
    }

    @Override
    public DetalleActividadVo updateDetail(DetalleActividadVo detalle) throws GenericException {
        return actividadesDao.updateDetail(detalle);
    }

    @Override
    public WrapperData<ActividadVo> getAllByStatusAndTipoActividadHorario(int page, int pageSize, String status, String param1, String swHorario) {
        return actividadesDao.getAllByStatusAndTipoActividadHorario(page, pageSize, status, param1, swHorario);
    }


}
