package com.github.rshtishi.department.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfiguration {

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin configureSwagger() {
		SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(this.springSwaggerConfig);
		ApiInfo apiInfo = new ApiInfoBuilder().title("Department Rest API")
				.description("Department API for creating and managing department").contact("randoshtishi@yahoo.com")
				.license("MIT License").licenseUrl("https://opensource.org/licenses/MIT").build();
		swaggerSpringMvcPlugin.apiInfo(apiInfo).apiVersion("1.0").includePatterns("/departments/*.*");
		return swaggerSpringMvcPlugin;
	}

}
