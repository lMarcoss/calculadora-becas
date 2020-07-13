package edu.calc.becas.mseguridad.menu.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Define las propiedades de menu de usuario
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Entidad con los datos del menu de usuario")
public class Menu {
    private int idMenu;
    private int idPadre;
    private String nombre;
    private boolean isCollapsed;
    private String icon;
    private String url;
    private List<Menu> childs;

}
