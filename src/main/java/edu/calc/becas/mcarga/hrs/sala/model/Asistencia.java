package edu.calc.becas.mcarga.hrs.sala.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 6/4/19
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Asistencia implements Serializable {
    private int asistencia;
    private int falta;
    private int porcentaje;
}
