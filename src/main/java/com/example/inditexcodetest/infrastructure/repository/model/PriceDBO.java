package com.example.inditexcodetest.infrastructure.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Data
@Entity
@Table(name = "PRICES")
public class PriceDBO {

  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "BRAND_ID")
  private String brandId;

  @Column(name = "START_DATE")
  private Instant startDate;

  @Column(name = "END_DATE")
  private Instant endDate;

  @Column(name = "PRICE_LIST")
  private String priceList;

  @Column(name = "PRODUCT_ID")
  private String productId;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CURR")
  private String currency;
}
