package com.example.inditexcodetest.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ProductRequest {

  private String productId;
  private String brandId;
  private Instant date;
}
