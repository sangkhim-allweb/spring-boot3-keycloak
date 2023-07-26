package com.sangkhim.spring_boot3_keycloak.config;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangkhim.spring_boot3_keycloak.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  public static final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      org.springframework.security.access.AccessDeniedException accessDeniedException)
      throws IOException {
    response.setStatus(SC_FORBIDDEN);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    ErrorResponse errorResponse =
        new ErrorResponse(
            String.valueOf(HttpStatus.FORBIDDEN.value()),
            accessDeniedException.getMessage(),
            TIMESTAMP);
    String json = new ObjectMapper().writeValueAsString(errorResponse);
    response.getWriter().write(json);
  }
}
