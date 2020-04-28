package edu.calc.becas.mcarga.hrs.alumnos.service;

import edu.calc.becas.common.model.CommonData;
import edu.calc.becas.exceptions.GenericException;
import edu.calc.becas.malumnos.model.Alumno;
import edu.calc.becas.mcarga.hrs.ProcessRow;
import edu.calc.becas.mcarga.hrs.alumnos.dao.CargaAlumnosPeriodosDao;
import edu.calc.becas.mcarga.hrs.read.files.model.RowFile;
import edu.calc.becas.mcatalogos.actividades.dao.ActividadesDaoImpl;
import edu.calc.becas.mcatalogos.actividades.model.ActividadVo;
import edu.calc.becas.mcatalogos.licenciaturas.model.Licenciatura;
import edu.calc.becas.mconfiguracion.cicloescolar.model.CicloEscolarVo;
import edu.calc.becas.mconfiguracion.parciales.model.Parcial;
import edu.calc.becas.mreporte.actividades.percent.activity.model.ReportPercentActivity;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cargaAlumnosPeriodosService")
public class CargaAlumnosPeriodosServiceImpl extends ProcessRow implements CargaAlumnosPeriodosService {
  private static final Logger LOG = LoggerFactory.getLogger(CargaAlumnosPeriodosServiceImpl.class);

  @Autowired
  CargaAlumnosPeriodosDao cargaAlumnosPeriodosDao;

  @Autowired
  ActividadesDaoImpl actividadesDaoImpl;

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



  @Override
  public int processData(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) throws GenericException {

    List<RowFile> rows = readRowsAlumnos(pages);


    List<Alumno> alumnos = new ArrayList<>();
    int rowIni = 0;
    for (RowFile row : rows) {
      if(rowIni!=0){
        Alumno alumno = new Alumno();
        ActividadVo actividadVo = new ActividadVo("S");
        actividadVo.setIdActividad(idActividadBiblioteca);
        alumno.setActividad(actividadVo);

        for (int i = 0; (i < row.getCells().size() && i <= posEndCell); i++) {

          if (i == posGrupo){
            alumno.setGrupo(row.getCells().get(i).getValue());
          }
          if (i == posMatricula){
            alumno.setMatricula(row.getCells().get(i).getValue());
          }
          if (i == posCurp){
            alumno.setCurp(row.getCells().get(i).getValue());
          }
          if (i == posApePaterno){
            alumno.setApePaterno(row.getCells().get(i).getValue());
          }
          if (i == posApeMaterno){
            alumno.setApeMaterno(row.getCells().get(i).getValue());
          }
          if (i == posNombres){
            alumno.setNombres(row.getCells().get(i).getValue());
          }
          if (i == posCodigoRFid){
            alumno.setCodigoRFid(row.getCells().get(i).getValue());
          }

        }
        // datos de auditoria
        alumno.setActualizadoPor(commonData.getActualizadoPor());
        alumno.setAgregadoPor(commonData.getAgregadoPor());

        alumnos.add(alumno);
      }
      rowIni++;

    }
    return cargaAlumnosPeriodosDao.persistenceAlumnos(alumnos,  parcialActual, cicloEscolarActual, licenciatura);
    }

    @Override
    public int processDataPorcentajes(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual, Licenciatura licenciatura) throws GenericException {

    List<RowFile> rows = readRowsAlumnosReportes(pages);


    List<ReportPercentActivity> alumnosReportes = new ArrayList<>();
    int rowIni = 0;
    for (RowFile row : rows) {

        ReportPercentActivity alumno = new ReportPercentActivity();
        ActividadVo actividadVo = new ActividadVo("S");
        actividadVo.setIdActividad(idActividadBiblioteca);


        for (int i = 0; (i < row.getCells().size() && i <= 32); i++) {

          if (i == 2){
            alumno.setMatricula(row.getCells().get(i).getValue());
          }
          if (i == 31){
            String valor = row.getCells().get(i).getValue();
            float tmp = Float.parseFloat(valor)*100;
            int b=(int)(Math.round(tmp));
            System.out.println("porcentaje :::::::: " + b);

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
    LOG.debug(alumnosReportes.toString());
     return actividadesDaoImpl.persistencePorcentaje(alumnosReportes,  parcialActual, cicloEscolarActual);
    }

  public static interface CargaAlumnosPeriodosService {
    int processData(Workbook pages, CommonData commonData, Parcial parcialActual, CicloEscolarVo cicloEscolarActual) throws GenericException;
  }
}
