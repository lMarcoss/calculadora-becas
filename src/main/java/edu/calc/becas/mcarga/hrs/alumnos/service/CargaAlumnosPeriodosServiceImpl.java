package edu.calc.becas.mcarga.hrs.alumnos.service;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.ProcessRow;
import edu.calc.becas.mcarga.hrs.alumnos.dao.CargaAlumnosPeriodosDao;
import edu.calc.becas.mcarga.hrs.alumnos.model.ProcessAlumnos;
import edu.calc.becas.mcarga.hrs.read.files.model.CellFile;
import edu.calc.becas.mcarga.hrs.read.files.model.RowFile;
import edu.calc.becas.mcatalogos.actividades.dao.ActividadesDaoImpl;
import edu.calc.becas.mcatalogos.grupos.model.Grupo;
import edu.calc.becas.mcatalogos.grupos.service.GrupoService;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mcatalogos.licenciaturas.service.LicenciaturaService;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("cargaAlumnosPeriodosService")
public class CargaAlumnosPeriodosServiceImpl extends ProcessRow implements CargaAlumnosPeriodosService {

    final
    CargaAlumnosPeriodosDao cargaAlumnosPeriodosDao;

    final
    ActividadesDaoImpl actividadesDaoImpl;

    final
    LicenciaturaService licenciaturaService;

    final
    GrupoService grupoService;

    @Value("${prop.carga.hrs.biblioteca.id}")
    private int idActividadBiblioteca;

    @Value("${prop.carga.alumnos.alumnos.posicion.matricula}")
    private int posMatricula;

    @Value("${prop.carga.alumnos.alumnos.posicion.curp}")
    private int posCurp;

    @Value("${prop.carga.alumnos.alumnos.posicion.apePaterno}")
    private int posApePaterno;

    @Value("${prop.carga.alumnos.alumnos.posicion.apeMaterno}")
    private int posApeMaterno;

    @Value("${prop.carga.alumnos.alumnos.posicion.nombres}")
    private int posNombres;

    @Value("${prop.carga.alumnos.alumnos.posicion.grupo}")
    private int posGrupo;

    @Value("${prop.carga.alumnos.alumnos.posicion.codigoRFid}")
    private int posCodigoRFid;

    @Value("${prop.carga.alumnos.alumnos.posicion.celda.final}")
    private int posEndCell = 4;

    public CargaAlumnosPeriodosServiceImpl(CargaAlumnosPeriodosDao cargaAlumnosPeriodosDao,
                                           ActividadesDaoImpl actividadesDaoImpl,
                                           LicenciaturaService licenciaturaService,
                                           GrupoService grupoService) {
        this.cargaAlumnosPeriodosDao = cargaAlumnosPeriodosDao;
        this.actividadesDaoImpl = actividadesDaoImpl;
        this.licenciaturaService = licenciaturaService;
        this.grupoService = grupoService;
    }


    @Override
    public ProcessAlumnos processData(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura, String grupo) throws GenericException {

        List<RowFile> rows = readRowsAlumnos(pages);


        List<Alumno> alumnos = new ArrayList<>();

        Grupo grupoAlumno;

        // obtiene el  grupo
        if (grupo.equalsIgnoreCase("All")) {
            String cveGrupo = obtieneClaveGrupo(rows.get(0), posGrupo);

            grupoAlumno = grupoService.getGrupoByClave(cveGrupo);

            if (grupoAlumno == null) {
                throw new GenericException(String.format("El grupo %s no está definido", cveGrupo));
            }
        } else {
            grupoAlumno = grupoService.getGrupoByClave(grupo);

        }


        rows.forEach(row -> {
            Alumno alumno = new Alumno();
            alumno.setIdLicenciatura(licenciatura.getCveLicenciatura());
            alumno.setLicenciatura(licenciatura.getNombreLicenciatura());


            alumno.setGrupo(grupoAlumno.getCveGrupo());
            alumno.setDsGrupo(grupoAlumno.getNombreGrupo());

            try {
                alumno.setMatricula(row.getCells().get(posMatricula).getValue());
                alumno.setCurp(row.getCells().get(posCurp).getValue());
                alumno.setApePaterno(row.getCells().get(posApePaterno).getValue());
                alumno.setApeMaterno(row.getCells().get(posApeMaterno).getValue());
                alumno.setNombres(row.getCells().get(posNombres).getValue());
                alumno.setCodigoRFid(row.getCells().get(posCodigoRFid).getValue());
            } catch (IndexOutOfBoundsException e) {
                //error al obtener los datos del alumno
                log.error("Error al  obtener los datos del alumno " + e.getMessage());
            }


            // datos de auditoria
            alumno.setActualizadoPor(commonData.getActualizadoPor());
            alumno.setAgregadoPor(commonData.getAgregadoPor());
            if (!alumno.getMatricula().equals("MATRICULA")) {
                alumnos.add(alumno);
            }
        });


        /*int rowIni = 0;
        for (RowFile row : rows) {

            try {
                Alumno alumno = new Alumno();
                alumno.setIdLicenciatura(licenciatura.getCveLicenciatura());
                alumno.setLicenciatura(licenciatura.getNombreLicenciatura());


                alumno.setGrupo(grupoAlumno.getCveGrupo());
                alumno.setDsGrupo(grupoAlumno.getNombreGrupo());

                try {
                    alumno.setMatricula(row.getCells().get(posMatricula).getValue());
                    alumno.setCurp(row.getCells().get(posCurp).getValue());
                    alumno.setApePaterno(row.getCells().get(posApePaterno).getValue());
                    alumno.setApeMaterno(row.getCells().get(posApeMaterno).getValue());
                    alumno.setNombres(row.getCells().get(posNombres).getValue());
                    alumno.setCodigoRFid(row.getCells().get(posCodigoRFid).getValue());
                } catch (IndexOutOfBoundsException e) {
                    log.error("Error al  obtener los datos del alumno " + e.getMessage());
                }




                *//*for (int i = 0; (i < row.getCells().size() && i <= posEndCell); i++) {

                    alumno.setGrupo(grupoAlumno.getCveGrupo());
                    alumno.setDsGrupo(grupoAlumno.getNombreGrupo());


                    if (i == posMatricula) {
                        alumno.setMatricula(row.getCells().get(i).getValue());
                    }
                    if (i == posCurp) {
                        alumno.setCurp(row.getCells().get(i).getValue());
                    }
                    if (i == posApePaterno) {
                        alumno.setApePaterno(row.getCells().get(i).getValue());
                    }
                    if (i == posApeMaterno) {
                        alumno.setApeMaterno(row.getCells().get(i).getValue());
                    }
                    if (i == posNombres) {
                        alumno.setNombres(row.getCells().get(i).getValue());
                    }
                    if (i == posCodigoRFid) {
                        alumno.setCodigoRFid(row.getCells().get(i).getValue());
                    }

                }*//*

                // datos de auditoria
                alumno.setActualizadoPor(commonData.getActualizadoPor());
                alumno.setAgregadoPor(commonData.getAgregadoPor());
                if (!alumno.getMatricula().equals("MATRICULA")) {
                    alumnos.add(alumno);
                }
            } catch (Exception e) {
                log.error("Error  al obtener registro de la fila " + rowIni + " " + e.getMessage());
            }


            rowIni++;

        }*/
        return cargaAlumnosPeriodosDao.persistenceAlumnos(alumnos, parcialActual, cicloEscolarActual, licenciatura);
    }

    private String obtieneClaveGrupo(RowFile registroAlumno, int columnaConClaveGrupo) throws GenericException {
        String cveGrupo = null;
        String[] datosGrupo = registroAlumno.getCells().get(columnaConClaveGrupo).getValue().split("-");

        if (datosGrupo.length > 1) {
            cveGrupo = datosGrupo[1].trim();
        } else if (datosGrupo.length == 1) {
            cveGrupo = datosGrupo[0].trim();
        }

        if (cveGrupo == null) {
            throw new GenericException("Error al obtener clave del grupo");
        }

        return cveGrupo;

    }

    @Override
    public int processDataPorcentajes(Workbook pages, CommonData commonData, Parcial parcialActual,
                                      CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) throws GenericException {

        List<RowFile> rows = readRowsAlumnosReportes(pages);


        List<ReportPercentActivity> alumnosReportes = new ArrayList<>();
        int rowIni = 0;
        for (RowFile row : rows) {

            ReportPercentActivity alumno = new ReportPercentActivity();
            //ActividadVo actividadVo = new ActividadVo("S");
            //actividadVo.setIdActividad(idActividadBiblioteca);


            for (int i = 0; (i < row.getCells().size() && i <= 32); i++) {

                if (i == 2) {
                    alumno.setMatricula(row.getCells().get(i).getValue());
                }
                if (i == 31) {
                    String valor = row.getCells().get(i).getValue();
                    float tmp = Float.parseFloat(valor) * 100;
                    int b = (int) (Math.round(tmp));
                    log.info("porcentaje :::::::: " + b);

            /*String[] arrOfStr = valor.split(".", 2);
            for (String a : arrOfStr)
              System.out.println(a);*/
                    alumno.setPorcentajeActividad(b);
                    //alumno.setPorcentajeActividad(Integer.parseInt(row.getCells().get(i).getValue()));
                }

            }
            alumnosReportes.add(alumno);

            rowIni++;

        }
        log.debug(alumnosReportes.toString());
        return actividadesDaoImpl.persistencePorcentaje(alumnosReportes, parcialActual, cicloEscolarActual);
    }

}
