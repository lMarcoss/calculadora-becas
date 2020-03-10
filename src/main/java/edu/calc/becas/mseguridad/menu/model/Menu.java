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
public class Menu {
    private int idMenu;
    private int idPadre;
    private String nombre;
    private boolean isCollapsed;
    private String icon;
    private String url;
    private List<Menu> childs;

}
