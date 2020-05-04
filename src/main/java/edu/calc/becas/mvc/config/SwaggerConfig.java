package edu.calc.becas.mvc.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Marcos Santiago Leonardo
 * Universidad de la Sierra Sur (UNSIS)
 * Description: Configuration swagger
 * Date: 5/30/19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket swaggerDocket() {
        List<ResponseMessage> responseMessages = responsesMessages();
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(Errors.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.calc.becas"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.PATCH, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/docs/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/docs/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/docs", "/docs/swagger-ui.html");
        registry.addRedirectViewController("/docs/", "/docs/swagger-ui.html");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/docs/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Sistema de Control de Becas UNSIS - API REST",
                "Servicios rest API para el Sistema de Control de Becas UNSIS",
                "v1.0.0",
                "Terms of service",
                new Contact("UNSIS", "http://www.unsis.edu.mx/", ""),
                "License of API", "API license URL", Collections.emptyList());
    }

    private List<ResponseMessage> responsesMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(200).message("Exitoso").message("Exitoso").build(),
                new ResponseMessageBuilder().code(401).message("No Autorizado").message("Acceso no autorizado").build(),
                new ResponseMessageBuilder().code(403).message("Prohibido").message("Acceso prohibido").build(),
                new ResponseMessageBuilder().code(404).message("No encontrado").message("Servicio no encontrado").build());
    }


    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
