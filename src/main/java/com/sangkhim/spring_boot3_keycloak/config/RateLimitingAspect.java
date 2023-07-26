package com.sangkhim.spring_boot3_keycloak.config;

import com.sangkhim.spring_boot3_keycloak.exception.TooManyRequestsException;
import com.sangkhim.spring_boot3_keycloak.utils.HttpUtils;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitingAspect {

  private final RateLimitConfig rateLimitConfig;

  @Around(value = "execution(* com.sangkhim.spring_boot3_keycloak.controller..*(..))")
  public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    String ip = HttpUtils.getRequestIP(request);
    Bucket bucket = rateLimitConfig.resolveBucket("ip-" + ip);
    if (bucket.tryConsume(1)) {
      joinPoint.proceed();
    } else {
      throw new TooManyRequestsException(
          MessageFormat.format("Rate limit exceeded for IP {0}", String.valueOf(ip)));
    }
  }
}