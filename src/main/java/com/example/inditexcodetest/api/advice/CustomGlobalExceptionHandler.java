package com.example.inditexcodetest.api.advice;

import com.example.inditexcodetest.api.util.GenericResponseEntity;
import com.example.inditexcodetest.domain.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
                                                                final HttpStatus status, final WebRequest request) {

    final String wrongFields = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField().concat(": ").concat(fieldError.getDefaultMessage()))
        .collect(Collectors.joining("; "));

    final GenericResponseEntity<Object> response = GenericResponseEntity.withError(new BadRequestException("Request data is not valid. ".concat(wrongFields)));

    return new ResponseEntity<>(response.getBody(), response.getStatusCode());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                        final HttpHeaders headers, final HttpStatus status,
                                                                        final WebRequest request) {
    final String wrongFields = ex.getParameterName();

    final GenericResponseEntity<Object> response = GenericResponseEntity.withError(new BadRequestException("Request data is not valid. Missing param: ".concat(wrongFields)));

    return new ResponseEntity<>(response.getBody(), response.getStatusCode());
  }
}
