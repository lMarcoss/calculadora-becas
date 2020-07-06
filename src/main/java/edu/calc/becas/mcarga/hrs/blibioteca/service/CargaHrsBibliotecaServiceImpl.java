package edu.calc.becas.mcarga.hrs.blibioteca.service;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.CargaHrsDao;
import edu.calc.becas.mcarga.hrs.ProcessHoursService;
import edu.calc.becas.mcarga.hrs.ProcessRow;
import edu.calc.becas.mcarga.hrs.blibioteca.model.Hora;
import edu.calc.becas.mcarga.hrs.read.files.model.RowFile;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 5/13/19
 */
@Service("cargaHrsBibliotecaService")
public class CargaHrsBibliotecaServiceImpl extends ProcessRow implements ProcessHoursService {
    private static final Logger LOG = LoggerFactory.getLogger(CargaHrsBibliotecaServiceImpl.class);

    @Value("${prop.carga.hrs.biblioteca.id}")
    private int idActividadBiblioteca;

    @Value("${prop.carga.hrs.biblioteca.posicion.matricula}")
    private int posMatricula;

    @Value("${prop.carga.hrs.biblioteca.posicion.nombre}")
    private int posNombre;

    @Value("${prop.carga.hrs.biblioteca.posicion.horas}")
    private int posHrs = 3;

    @Value("${prop.carga.hrs.biblioteca.posicion.celda.final}")
    private int posEndCell = 4;

    private final CargaHrsDao cargaHrsBibliotecaDao;


    @Autowired
    public CargaHrsBibliotecaServiceImpl(
            @Qualifier("cargaHrsBibliotecaRepository") CargaHrsDao cargaHrsBibliotecaDao) {
        this.cargaHrsBibliotecaDao = cargaHrsBibliotecaDao;
    }


    @Override
    public int processData(Workbook pages, CommonData commonData, Parcial parcialCarga,
                           CicloEscolarVo cicloEscolarActual) throws GenericException {

        List<RowFile> rows = readRows(pages);

        List<Alumno> alumnos = new ArrayList<>();
        for (RowFile row : rows) {

            Alumno alumno = new Alumno();
            ActividadVo actividadVo = new ActividadVo("S");
            actividadVo.setIdActividad(idActividadBiblioteca);
            alumno.setActividad(actividadVo);

            for (int i = 0; (i < row.getCells().size() && i <= posEndCell); i++) {
                if (i == posMatricula) {
                    alumno.setMatricula(row.getCells().get(i).getValue());
                }

                if (i == posNombre) {
                    alumno.setNombres(row.getCells().get(i).getValue());
                }

                if (i == posHrs) {
                    try {
                        String hrs = row.getCells().get(i).getValue();
                        String[] horas = hrs.split(":");
                        Hora hora = new Hora();
                        if (horas.length == 2) {
                            hora.setNumHora(Integer.parseInt(horas[0]));
                            hora.setNumMinutos(Integer.parseInt(horas[1]));
                        } else if (horas.length == 1) {
                            hora.setNumHora(Integer.parseInt(horas[0]));
                            hora.setNumMinutos(0);
                        }
                        alumno.setHora(hora);
                    } catch (Exception e) {
                        LOG.error("ERROR AL OBTENER N\u00daMERO DE HORAS");
                        LOG.error(Arrays.toString(e.getStackTrace()));
                    }
                }
            }

            // datos de auditoria
            alumno.setActualizadoPor(commonData.getActualizadoPor());
            alumno.setAgregadoPor(commonData.getAgregadoPor());

            alumnos.add(alumno);
        }

        return this.cargaHrsBibliotecaDao.persistenceHours(alumnos, parcialCarga, cicloEscolarActual);
    }
}
