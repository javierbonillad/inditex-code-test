package com.example.inditexcodetest.api.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

  private T data;
  private GenericResponseError error;
}
