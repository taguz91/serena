package com.taguz91.api_serena;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class ApiSerenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSerenaApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
			.components(
					new Components().addSecuritySchemes("basicScheme",
					new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
			).info(
					new Info().title("Serena API").version(appVersion)
					.description("This is a sample application, using JWT.")
					.license(new License().name("Apache 2.0"))
			);
	}
}
