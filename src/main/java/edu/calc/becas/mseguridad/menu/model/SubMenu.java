package edu.calc.becas.mseguridad.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubMenu {
    private String nombre;
    private String url;
    private String path;
    private String outlet;
    private List<String> permisos;
}
