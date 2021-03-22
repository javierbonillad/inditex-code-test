package com.example.inditexcodetest.domain.exception;

import lombok.Getter;

@Getter
public class InditexException extends RuntimeException {

  private final Integer httpCode = 500;
  private final String code = "G500";

  public InditexException(final String message) {
    super(message);
  }

  public InditexException(final Throwable throwable) {
    super(throwable);
  }

  public InditexException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
