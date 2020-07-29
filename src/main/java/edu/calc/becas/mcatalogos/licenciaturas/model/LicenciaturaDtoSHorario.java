package edu.calc.becas.mcatalogos.licenciaturas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * dto para la comunicacion con el sistema de horarios del objeto licenciatura
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 08/03/20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaturaDtoSHorario {
    private String clave;
    private String nombre;
    private Boolean vigente;

}
