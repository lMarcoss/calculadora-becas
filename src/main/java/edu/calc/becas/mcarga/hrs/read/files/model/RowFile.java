package edu.calc.becas.mcarga.hrs.read.files.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Define las propiedades de una fila o registro e excel
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 5/13/19
 */
@NoArgsConstructor
@Getter
@Setter
public class RowFile {
    private List<CellFile> cells;
}
