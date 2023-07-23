package com.sangkhim.spring_boot3_keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBoot3KeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBoot3KeycloakApplication.class, args);
  }
}
