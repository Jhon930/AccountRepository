package com.account.ms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
			    .apis(RequestHandlerSelectors.basePackage("com.account.ms.controller"))
			    .paths(PathSelectors.regex("/accounts.*"))
			    .build();
	}

	/*private Predicate<String> postPaths() {
		return or(regex("/api/posts.*"), regex("/personas/listar.*"));
	}

    private ApiInfo usersApiInfo() {

        return new ApiInfoBuilder()
                .title("Service User")
                .version("1.0")
                .license("Apache License Version 2.0")
                .build();
    }*/


}
