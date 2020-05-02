package edu.calc.becas.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur
 * Description: Datos de paginación para consultas
 * Date: 2019-07-10
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pageable implements Serializable {
    private int page;
    private int pageSize;
}
