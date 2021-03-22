package com.example.inditexcodetest.domain.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends InditexException {

  private final Integer httpCode = 400;
  private final String code = "BR001";

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(Throwable throwable) {
    super(throwable);
  }

  public BadRequestException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
