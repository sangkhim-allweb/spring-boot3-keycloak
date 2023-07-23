package com.sangkhim.spring_boot3_keycloak.exception;

import com.sangkhim.spring_boot3_keycloak.exception.base.ServiceException;

/** trigger for data not found exception */
public class DataNotFoundException extends ServiceException {

  public DataNotFoundException() {
    super();
  }

  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
