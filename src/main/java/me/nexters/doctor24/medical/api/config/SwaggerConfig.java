package me.nexters.doctor24.medical.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author manki.kim
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		OpenAPI openAPI = new OpenAPI();
		Info info = new Info();
		info.setTitle("Todoc APIs");
		info.description("serving medical service information");
		info.setVersion("v1");
		openAPI.setInfo(info);
		return openAPI;
	}
}
