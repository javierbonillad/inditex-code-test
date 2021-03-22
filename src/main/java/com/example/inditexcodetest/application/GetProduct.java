package com.example.inditexcodetest.application;

import com.example.inditexcodetest.domain.ProductService;
import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import io.vavr.Function3;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class GetProduct implements Function3<String, String, Instant, Either<InditexException, Product>> {

  private final transient ProductService productService;

  @Override
  public Either<InditexException, Product> apply(final String productId, final String brandId, final Instant date) {
    return productService.getProduct(buildProductRequest(productId, brandId, date));
  }

  private ProductRequest buildProductRequest(final String productId, final String brandId, final Instant date) {
    return ProductRequest.builder()
        .productId(productId)
        .brandId(brandId)
        .date(date)
        .build();
  }
}
