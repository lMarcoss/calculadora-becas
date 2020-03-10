package edu.calc.becas.mconfiguracion.cicloescolar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: dto para la comunicacion con el sistema de horarios para el objeto ciclo escolar
 * Date: 08/03/20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoDtoSHorario {
    String clave;
    String ffin;
    String finicio;
    String nombre;
    String tipo;
}
