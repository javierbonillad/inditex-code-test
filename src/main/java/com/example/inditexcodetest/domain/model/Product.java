package com.example.inditexcodetest.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

  private String productId;

  private String brandId;

  private String priceList;

  private DateRange dateRange;

  private Money amount;
}
