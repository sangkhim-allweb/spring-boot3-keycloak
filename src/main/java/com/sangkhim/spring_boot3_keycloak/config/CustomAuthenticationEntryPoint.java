package com.sangkhim.spring_boot3_keycloak.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangkhim.spring_boot3_keycloak.exception.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  public static final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    ErrorResponse errorResponse =
        new ErrorResponse(
            String.valueOf(HttpStatus.UNAUTHORIZED.value()), authException.getMessage(), TIMESTAMP);
    String json = new ObjectMapper().writeValueAsString(errorResponse);
    response.getWriter().write(json);
  }
}
