package edu.calc.becas.mconfiguracion.cicloescolar.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cicloescolar")
@Api(description = "Servicios para consulta de ciclos escolares")
public class CicloEscolarAPI {

    private CicloEscolarService cicloEscolarService;

    @Autowired
    public CicloEscolarAPI(CicloEscolarService cicloEscolarService) {
        this.cicloEscolarService = cicloEscolarService;
    }


    @GetMapping
    @ApiOperation(value = "Obtiene la lista de ciclos escolares")
    public WrapperData<CicloEscolarVo> getAll() throws GenericException {
        return cicloEscolarService.getAll();
    }

    @GetMapping("/actual")
    @ApiOperation(value = "Obtiene el ciclo escolar actual")
    public CicloEscolarVo getCicloEscolarActual() throws GenericException {
        return cicloEscolarService.getCicloEscolarActual();
    }


}
