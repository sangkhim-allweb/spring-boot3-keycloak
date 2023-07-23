package com.sangkhim.spring_boot3_keycloak;

import java.util.Iterator;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableCaching
public class SpringBoot3KeycloakApplication {

  public static void main(String[] args) {
    // list all the caching provider
    Iterator<CachingProvider> iterator =
        Caching.getCachingProviders(Caching.getDefaultClassLoader()).iterator();
    while (iterator.hasNext()) {
      CachingProvider provider = iterator.next();
      if (!(provider instanceof EhcacheCachingProvider)) {
        iterator.remove();
      }
    }
    SpringApplication.run(SpringBoot3KeycloakApplication.class, args);
  }
}
