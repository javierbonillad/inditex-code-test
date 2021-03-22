package com.example.inditexcodetest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyDTO {

  /**
   * Amount of money
   */
  private String amount;

  /**
   * ISO currency ID
   */
  private String currency;
}
