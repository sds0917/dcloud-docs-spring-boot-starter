package io.github.sds0917.docs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DocsConfiguration {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.DELETE);
		config.setAllowCredentials(true);
		log.debug("Registering CORS filter");
		source.registerCorsConfiguration("/api/**", config);
		source.registerCorsConfiguration("/oauth/**", config);
		source.registerCorsConfiguration("/v2/**", config);
		return new CorsFilter(source);
	}

}