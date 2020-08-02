package edu.calc.becas.mcarga.hrs.blibioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Define las  propiedades de una Hora de biblioteca o horario de actividad
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 6/4/19
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Hora implements Serializable {
    private String idHora;
    private int numHora;
    private int numMinutos;
    private String amPm;
}
