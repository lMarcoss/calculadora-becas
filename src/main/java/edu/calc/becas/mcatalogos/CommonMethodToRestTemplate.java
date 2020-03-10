package edu.calc.becas.mcatalogos;

import edu.calc.becas.mvc.config.MessageApplicationProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description:
 * Date: 09/03/20
 */
@Component
public class CommonMethodToRestTemplate {

    @Value("${prop.sistema.horarios.url}")
    protected String urlSistemaHorarios;

    protected final RestTemplate restTemplate;
    protected final MessageApplicationProperty messageApplicationProperty;

    protected HttpHeaders headers;

    public CommonMethodToRestTemplate(RestTemplate restTemplate, MessageApplicationProperty messageApplicationProperty) {
        this.restTemplate = restTemplate;
        this.messageApplicationProperty = messageApplicationProperty;

        this.headers = new HttpHeaders();
        this.headers.set("Accept", "application/json");
    }
}