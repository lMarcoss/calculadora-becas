package edu.calc.becas.mconfiguracion.parciales.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import edu.calc.becas.mseguridad.login.model.UserLogin;
import edu.calc.becas.mvc.config.security.user.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static edu.calc.becas.common.utils.Constant.ESTATUS_ACTIVE;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 4/9/19
 */
@RestController
@RequestMapping("/parciales")
@Api(description = "Servicios para administración de parciales")
public class ParcialAPI {

    private final ParcialService parcialService;
    private final CicloEscolarService cicloEscolarService;
    private final UserRequestService userRequestService;

    @Autowired
    public ParcialAPI(ParcialService parcialService, CicloEscolarService cicloEscolarService,
                      UserRequestService userRequestService) {
        this.parcialService = parcialService;
        this.cicloEscolarService = cicloEscolarService;
        this.userRequestService = userRequestService;
    }

    @GetMapping("/periodo-actual")
    @ApiOperation("Obtiene el listado de parciales del periodo actual")
    public WrapperData<Parcial> getParcialesPeriodoActual() throws GenericException {
        CicloEscolarVo cicloEscolar = cicloEscolarService.getCicloEscolarActual();
        String cvePeriodo = cicloEscolar.getClave();

        WrapperData<Parcial> parcialWrapperData = new WrapperData<>();
        parcialWrapperData.setData(this.parcialService.getAllByPeriodo(cvePeriodo));
        parcialWrapperData.setPage(0);
        parcialWrapperData.setPageSize(parcialWrapperData.getData().size());
        parcialWrapperData.setLengthData(parcialWrapperData.getData().size());
        return parcialWrapperData;
    }

    @GetMapping("/carga-horas-biblioteca")
    @ApiOperation("Obtiene el listado de parciales disponibles para carga de horas de biblioteca")
    public List<Parcial> getParcialesPeriodoActualCargaHorasBiblioteca(HttpServletRequest httpServletRequest) throws GenericException {
        UserLogin userLogin = userRequestService.getUserLogin(httpServletRequest);

        return parcialService.getParcialesPeriodoActualCargaHorasBiblioteca(userLogin);
    }

    @GetMapping("/actual")
    @ApiOperation("Obtiene el parcial actual")
    public Parcial getParcialActual() throws GenericException {
        return parcialService.getParcialActual();
    }

    @GetMapping()
    @ApiOperation("Obtiene el cat\u00e1lgoo de parciales")
    public WrapperData<Parcial> getAll() throws GenericException {
        return this.parcialService.getAllByStatus(0, 0, ESTATUS_ACTIVE);
    }

    @PatchMapping
    @ApiOperation("Actualiza datos del parcial")
    public Parcial update(@RequestBody Parcial parcial, HttpServletRequest httpServlet) {
        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);
        parcial.setActualizadoPor(userLogin.getUsername());
        parcial.setAgregadoPor(userLogin.getUsername());
        return this.parcialService.update(parcial);
    }

    @PostMapping
    @ApiOperation("Registra un nuevo parcial")
    public Parcial add(@RequestBody Parcial parcial, HttpServletRequest httpServlet) throws Exception {

        UserLogin userLogin = userRequestService.getUserLogin(httpServlet);

        parcial.setAgregadoPor(userLogin.getUsername());
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        parcial.setCvePeriodo(cicloEscolarVo.getClave());
        parcial.setDescPeriodo(cicloEscolarVo.getNombre());
        return this.parcialService.add(parcial);
    }


}
