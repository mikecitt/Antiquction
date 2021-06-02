package com.scopic.antiquction.config;

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
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.scopic.antiquction.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API")
				.description("Spring Boot REST API for Antiquction")
				.termsOfServiceUrl("http://javainuse.com")
                .version("2.0")
				.license("Java 11 License").build();
	}
}
