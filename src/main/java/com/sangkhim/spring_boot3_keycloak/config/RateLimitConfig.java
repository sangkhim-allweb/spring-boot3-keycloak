package com.sangkhim.spring_boot3_keycloak.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {

  @Autowired public ProxyManager buckets;

  public Bucket resolveBucket(String key) {
    Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser();

    // Does not always create a new bucket, but instead returns the existing one if it exists.
    return buckets.builder().build(key, configSupplier);
  }

  private Supplier<BucketConfiguration> getConfigSupplierForUser() {
    Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(20, refill);

    return () -> (BucketConfiguration.builder().addLimit(limit).build());
  }
}
