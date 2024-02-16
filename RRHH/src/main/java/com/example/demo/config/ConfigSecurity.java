package com.example.demo.config;

import javax.sql.DataSource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class ConfigSecurity {
	@Bean
	public UserDetailsManager userDetailsManager(DataSource datasource) {
		
		return new JdbcUserDetailsManager(datasource);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests( configure ->{
			configure
				.requestMatchers(HttpMethod.GET, "/trivial/pregunta").hasRole("usuario")
				.requestMatchers(HttpMethod.GET, "/trivial/pregunta/**").hasRole("usuario")
					.requestMatchers(HttpMethod.GET,"/v2/api-docs",
							"/swagger-resources",
							"/swagger-resources/**",
							"/configuration/ui",
							"/configuration/security",
							"/swagger-ui.html",
							"/webjars/**",
							"/v3/api-docs/**",
							"/swagger-ui/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/trivial/pregunta").hasRole("administrador")
				.requestMatchers(HttpMethod.PUT, "/trivial/pregunta/**").hasRole("administrador")
				.requestMatchers(HttpMethod.DELETE, "/trivial/pregunta/**").hasRole("administrador");
				
		});
		
		http.httpBasic(Customizer.withDefaults());
		
		http.csrf( csrf -> csrf.disable());
		
		return http.build();
		
	}
}