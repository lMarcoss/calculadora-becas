package edu.calc.becas.mconfiguracion.parciales.api;

import edu.calc.becas.common.model.WrapperData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.cicloescolar.service.CicloEscolarService;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mconfiguracion.parciales.service.ParcialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public ParcialAPI(ParcialService parcialService, CicloEscolarService cicloEscolarService) {
        this.parcialService = parcialService;
        this.cicloEscolarService = cicloEscolarService;
    }

    @GetMapping()
    @ApiOperation("Obtiene el listado de parciales del periodo actual")
    public WrapperData<Parcial> getAll() throws GenericException {
        CicloEscolarVo cicloEscolar = cicloEscolarService.getCicloEscolarActual();
        String cvePeriodo = cicloEscolar.getClave();

        WrapperData<Parcial> parcialWrapperData = new WrapperData<>();
        parcialWrapperData.setData(this.parcialService.getAllByPeriodo(cvePeriodo));
        parcialWrapperData.setPage(0);
        parcialWrapperData.setPageSize(parcialWrapperData.getData().size());
        parcialWrapperData.setLengthData(parcialWrapperData.getData().size());
        return parcialWrapperData;
    }

    @PatchMapping
    @ApiOperation("Actualiza datos del parcial")
    public Parcial update(@RequestBody Parcial parcial) {
        return this.parcialService.update(parcial);
    }

    @PostMapping
    @ApiOperation("Registra un nuevo parcial")
    public Parcial add(@RequestBody Parcial parcial) throws Exception {

        parcial.setAgregadoPor("admin");
        CicloEscolarVo cicloEscolarVo = cicloEscolarService.getCicloEscolarActual();
        parcial.setCvePeriodo(cicloEscolarVo.getClave());
        parcial.setDescPeriodo(cicloEscolarVo.getNombre());
        return this.parcialService.add(parcial);
    }


}
