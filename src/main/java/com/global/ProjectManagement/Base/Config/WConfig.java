package com.global.ProjectManagement.Base.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("https://briskshop.net", "http://localhost:4200", "https://admin.briskshop.net",
						"https://onefit.briskshop.net")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("Authorization", "Content-Type", "Accept").allowCredentials(true).maxAge(3600); // Cache
																												// the
																												// preflight
																												// response
																												// for 1
																												// hour
	}
}
