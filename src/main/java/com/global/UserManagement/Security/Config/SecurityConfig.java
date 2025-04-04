package com.global.UserManagement.Security.Config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.global.UserManagement.Security.Filter.AuthFilter;
import com.global.UserManagement.Security.Utils.JwtUnAuthResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public final String[] PUBLIC_ENDPOINTS = { "/api/v1/auth/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
			"/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/index.html", "/api/v2/category/findall",
			"/api/v2/product/newarrivals", "/api/v2/productpaginate/findallbycategoryname/**",
			"/api/v2/productpaginate/findallbycategorytypename/**", "/api/v2/product/findbyname/**",
			"/api/v2/productpaginate/getdiscountupto50/**", "/api/v2/productpaginate/getdiscount/**",
			"/api/v2/productpaginate/findbygenderandcategoryid/**", "/api/v2/user/purchase-requests",
			"/api/v2/user/shipping/findall", "/api/v2/names/byCategoryName", "/api/v2/usepromoCode/**" };

	@Autowired
	private JwtUnAuthResponse jwtUnAuthResponse;

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
					auth.requestMatchers("/api/v2/user/**").hasAuthority("user");
					auth.requestMatchers("/api/v2/admin/**").hasAuthority("admin");
					auth.anyRequest().authenticated();
				}).httpBasic(Customizer.withDefaults())
				.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtUnAuthResponse))
				.cors(cors -> cors.configurationSource(corsConfigurationSource())); // Integrate CORS configuration

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthFilter authFilter() {
		return new AuthFilter();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
				Arrays.asList("https://briskshop.net", "http://localhost:4200", "https://admin.briskshop.net",
						"https://onefit.briskshop.net", "https://darkorchid-badger-283713.hostingersite.com"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // Apply CORS to all endpoints
		return source;
	}
}
