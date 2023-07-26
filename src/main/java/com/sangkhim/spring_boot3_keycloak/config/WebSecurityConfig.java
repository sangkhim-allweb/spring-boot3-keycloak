package com.sangkhim.spring_boot3_keycloak.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
    http.securityMatcher("/**")
        .authorizeHttpRequests(
            rmr ->
                rmr.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**")
                    .permitAll());

    // h2-console
    http.securityMatcher(toH2Console())
        .authorizeHttpRequests(rmr -> rmr.requestMatchers(toH2Console()).permitAll())
        .csrf(AbstractHttpConfigurer::disable)
        .headers(
            httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer.frameOptions(
                    HeadersConfigurer.FrameOptionsConfig::disable));

    http.securityMatcher("/v1/**")
        .authorizeHttpRequests(
            rmr ->
                rmr.requestMatchers("/v1/authors/**", "/v1/tags/**")
                    .hasRole(ADMIN)
                    .requestMatchers("/v1/authors/**", "/v1/tags/**")
                    .hasAnyRole(ADMIN, USER)
                    .requestMatchers("/v1/**")
                    .authenticated());

    http.oauth2ResourceServer(
        oauth2ResourceServer ->
            oauth2ResourceServer
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));

    http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS));

    return http.build();
  }
}
