package com.sangkhim.spring_boot3_keycloak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  public static final String ADMIN = "admin";
  public static final String USER = "user";
  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/v1/authors/**", "/v1/tags/**")
        .hasRole(ADMIN)
        .requestMatchers(HttpMethod.GET, "/v1/**")
        .hasAnyRole(ADMIN, USER)
        .anyRequest()
        .authenticated();
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }
}
