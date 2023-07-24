package com.sangkhim.spring_boot3_keycloak.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

  private static final String REDIRECT_URL = "/swagger-ui.html";
  private static final String OAUTH_SCHEME_NAME = "client-sangkhim";

  @Value("${spring.mvc.servlet.path}")
  private String baseUrl;

  @Value("${keycloak.auth-server-url}")
  String authServerUrl;

  @Value("${keycloak.realm}")
  String realm;

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", baseUrl.concat(REDIRECT_URL));
    registry.addRedirectViewController("/swagger-ui", baseUrl.concat(REDIRECT_URL));
    registry.addRedirectViewController("/api", baseUrl.concat(REDIRECT_URL));
  }

  @Bean
  public OpenAPI apiDocConfig() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Spring Boot 3 Keycloak")
                .description("")
                .version("0.0.1")
                .contact(new Contact().name("sangkhim").email("sangkhim@gmail.com")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Documentation")
                .url("https://t.me/boreytechnology"))
        .components(
            new Components()
                .addSecuritySchemes(
                    OAUTH_SCHEME_NAME,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .in(SecurityScheme.In.HEADER)
                        .flows(new OAuthFlows().password(new OAuthFlow().tokenUrl(authServerUrl)))))
        .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
  }
}
