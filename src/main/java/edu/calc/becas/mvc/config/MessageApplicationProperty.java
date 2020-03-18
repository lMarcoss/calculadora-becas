package edu.calc.becas.mvc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 07/03/20
 */
@Component
@Setter
@Getter
@PropertySource("classpath:message-application.properties")
@ConfigurationProperties(prefix = "prop.message")
public class MessageApplicationProperty {
    String usuarioNoRegistrado;
    String errorObtenerLicencuaturasSistemaHorario;
    String errorObtenerDetalleLicencuaturaSistemaHorario;
    String errorObtenerPeriodoActual;
    String errorObtenerGrupos;
    String errorAsignarUsuariosAHorarioActividad;
}
