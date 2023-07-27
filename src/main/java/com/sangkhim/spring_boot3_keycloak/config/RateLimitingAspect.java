package com.sangkhim.spring_boot3_keycloak.config;

import com.sangkhim.spring_boot3_keycloak.exception.TooManyRequestsException;
import com.sangkhim.spring_boot3_keycloak.utils.HttpUtils;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitingAspect {

  private final RateLimitConfig rateLimitConfig;

  @Around(value = "execution(* com.sangkhim.spring_boot3_keycloak.controller..*(..))")
  public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request =
        ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
            .getRequest();
    String ip = HttpUtils.getRequestIP(request);
    Bucket bucket = rateLimitConfig.resolveBucket(ip + "-" + request.getRequestURI());
    if (bucket.tryConsume(1)) {
      return joinPoint.proceed();
    } else {
      String message =
          MessageFormat.format(
              "Rate limit exceeded for IP {0} - {1}", String.valueOf(ip), request.getRequestURI());
      log.warn(message);
      throw new TooManyRequestsException(message);
    }
  }
}
