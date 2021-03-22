package com.example.inditexcodetest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateRangeDTO {

  /**
   * Start date of the range
   */
  private Instant startDate;

  /**
   * End date of the range
   */
  private Instant endDate;
}
