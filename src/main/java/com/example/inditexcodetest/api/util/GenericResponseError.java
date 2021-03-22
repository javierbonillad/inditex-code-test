package com.example.inditexcodetest.api.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseError {

  private String code;
  private String message;
  @JsonIgnore
  private Throwable exception;
}
