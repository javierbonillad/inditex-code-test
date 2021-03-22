package com.example.inditexcodetest.api.util;

import com.example.inditexcodetest.domain.exception.InditexException;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenericResponseEntity<T> extends ResponseEntity<GenericResponse<T>> {

  public GenericResponseEntity(GenericResponse<T> body, HttpStatus status) {
    super(body, status);
  }

  public static <T> GenericResponseEntity<T> fromEither(final Either<GenericResponseEntity<T>, GenericResponseEntity<T>> either) {
    return either.isRight() ? either.get() : either.getLeft();
  }

  public static <T> GenericResponseEntity<T> withData(final T data, final HttpStatus httpStatus) {
    final GenericResponse<T> response = GenericResponse.<T>builder()
        .data(data)
        .build();

    return new GenericResponseEntity<>(response, httpStatus);
  }

  public static <T> GenericResponseEntity<T> withData(final T data) {
    return withData(data, HttpStatus.OK);
  }

  public static <T> GenericResponseEntity<T> withError(final Throwable throwable) {
    if (throwable instanceof InditexException) {
      return withDomainError((InditexException) throwable);
    }

    final GenericResponse<T> response = GenericResponse.<T>builder()
        .error(GenericResponseError.builder()
            .code("Generic code")
            .message(throwable.getMessage())
            .build())
        .build();

    return new GenericResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private static <T> GenericResponseEntity<T> withDomainError(final InditexException exception) {
    final HttpStatus status = HttpStatus.valueOf(exception.getHttpCode() != null ? exception.getHttpCode() : 500);
    final GenericResponse<T> response = GenericResponse.<T>builder()
        .error(GenericResponseError.builder()
            .code(exception.getCode())
            .message(exception.getMessage())
            .build())
        .build();

    return new GenericResponseEntity<>(response, status);
  }
}
