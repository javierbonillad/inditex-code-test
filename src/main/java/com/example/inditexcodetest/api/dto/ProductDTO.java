package com.example.inditexcodetest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

  /**
   * ID of the product
   */
  private String productId;

  /**
   * ID of the brand
   */
  private String brandId;

  /**
   * ID of the rate to be applied
   */
  private String priceList;

  /**
   * Range of dates when this rate and price is valid
   */
  private DateRangeDTO dateRange;

  /**
   * Amount to be applied for this product
   */
  private MoneyDTO amount;
}
