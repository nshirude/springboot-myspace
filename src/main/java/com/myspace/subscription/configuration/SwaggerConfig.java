package com.myspace.subscription.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket subscriptionApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)        		
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myspace.subscription.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Subscription REST Services API")
            .description("Documentation for the Subscription REST webservices API")
            .version("Version 1.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-1.0")
            .build();
    }
    
    
}