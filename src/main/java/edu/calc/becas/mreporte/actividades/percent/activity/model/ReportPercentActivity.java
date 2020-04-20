package edu.calc.becas.mreporte.actividades.percent.activity.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Meltsan Solutions
 * Description:
 * Date: 2019-07-10
 */
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Entidad para definir datos del reporte de actividades")
public class ReportPercentActivity implements Serializable {
    private int idPorcentajeBeca;
    private int idAlumno;
    private String matricula;
    private String nombres;
    private String apePaterno;
    private String apeMaterno;
    private int idActividadAlumno;
    private int porcentajeSala;
    private int porcentajeBiblioteca;
    private int porcentajeActividad;
    private int idActividad;
    private String nombreActividad;
    private int idParcial;
    private String descParcial;
    private String cvePeriodo;
    private String descPeriodo;
    private String cveGrupo;
    private String descGrupo;
    private String descLicenciatura;
    private String cveLicenciatura;
    private String palabraClave;
    private String cveEstatus;
    private String descEstatus;
}
