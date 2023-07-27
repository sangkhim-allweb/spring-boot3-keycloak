package com.sangkhim.spring_boot3_keycloak;

import java.util.Iterator;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.junit.jupiter.api.Test;
import org.redisson.jcache.JCachingProvider;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBoot3H2ApplicationTests {

  public SpringBoot3H2ApplicationTests() {
    // list all the caching provider
    Iterator<CachingProvider> iterator =
        Caching.getCachingProviders(Caching.getDefaultClassLoader()).iterator();
    while (iterator.hasNext()) {
      CachingProvider provider = iterator.next();
      if (!(provider instanceof JCachingProvider)) {
        iterator.remove();
      }
    }
  }

  @Test
  void contextLoads() {}
}
