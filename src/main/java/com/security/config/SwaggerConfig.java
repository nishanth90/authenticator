package com.security.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration to enable swagger UI
 * http://<host>:<port>/swagger-ui.html
 * */
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                     
          .apis(RequestHandlerSelectors.basePackage("com.security.controller"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    /*
     * Information to be displayed in the Swagger UI
     * */
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "JWT Authenticator service", 
          "This service generates a JSON Web Token for the registered users", 
          "v1.0", 
          "Terms of service", 
          new Contact("microservice", "www.microservice.com", "myeaddress@microservice.com"), 
          "License of API", "API license URL", Collections.emptyList());
        }
}