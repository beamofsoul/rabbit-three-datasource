package com.moraydata.general.management.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	@Value("${swagger.api.title}")  
    String title;
	
	@Value("${swagger.api.description}")  
    String description;
	
	@Value("${swagger.api.version}")  
    String version;
	
	@Value("${swagger.api.author.name}")  
    String authorName;
	
	@Value("${swagger.api.author.url}")  
    String url;
	
	@Value("${swagger.api.author.email}")  
    String email;
	
	@Bean
	public Docket createRestApi() {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		parameters.add(parameterBuilder
				.name("Authorization")
				.description("access_token in header")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build());
		
		parameters.add(parameterBuilder
				.name("access_token")
				.description("access_token in query")
				.modelRef(new ModelRef("string"))
				.parameterType("query")
				.required(false).build());
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.moraydata.general.primary.controller.openapi"))
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.build()
				.globalOperationParameters(parameters)
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.contact(new Contact(authorName, url, email))
				.version(version)
				.build();
	}
}
