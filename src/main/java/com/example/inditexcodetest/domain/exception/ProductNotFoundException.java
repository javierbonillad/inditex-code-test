package com.example.inditexcodetest.domain.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends InditexException {

  private final Integer httpCode = 404;
  private final String code = "P001";

  public ProductNotFoundException(String message) {
    super(message);
  }

  public ProductNotFoundException(Throwable throwable) {
    super(throwable);
  }

  public ProductNotFoundException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
