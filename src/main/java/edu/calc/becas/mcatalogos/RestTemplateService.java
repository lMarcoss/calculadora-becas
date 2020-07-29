package edu.calc.becas.mcatalogos;

import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Define los metodos comunes para comunicacion con servicios del sistema de horario mediante rest-template
 *
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Date: 09/03/20
 */
@Component
public class RestTemplateService {

    @Value("${prop.sistema.horarios.url}")
    protected String urlSistemaHorarios;

    protected final RestTemplate restTemplate;
    protected final MessageApplicationProperty messageApplicationProperty;

    protected HttpHeaders headers;

    /**
     * Inicializa rest-template
     *
     * @param restTemplate
     * @param messageApplicationProperty
     */
    public RestTemplateService(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty) {
        this.restTemplate = restTemplate;
        this.messageApplicationProperty = messageApplicationProperty;

        this.headers = new HttpHeaders();
        this.headers.set("Accept", "application/json");
    }
}
