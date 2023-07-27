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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitingAspect {

  private final Logger LOG = LoggerFactory.getLogger(getClass());

  private final RateLimitConfig rateLimitConfig;

  @Around(value = "execution(* com.sangkhim.spring_boot3_keycloak.controller..*(..))")
  public void aroundAdvice(ProceedingJoinPoint joinPoint) {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    LOG.warn(
        "Rate limit exceeded for IP: "
            + HttpUtils.getRequestIP(request)
            + " - "
            + request.getRequestURI());

    String ip = HttpUtils.getRequestIP(request);
    Bucket bucket = rateLimitConfig.resolveBucket("ip-" + ip);
    if (bucket.tryConsume(1)) {
      try {
        joinPoint.proceed();
      } catch (Throwable e) {
        LOG.error("Exception: " + e.getMessage());
      }
    } else {
      LOG.warn(
          "Rate limit exceeded for IP: "
              + HttpUtils.getRequestIP(request)
              + " - "
              + request.getRequestURI());
      throw new TooManyRequestsException(
          MessageFormat.format("Rate limit exceeded for IP {0}", String.valueOf(ip)));
    }
  }
}
