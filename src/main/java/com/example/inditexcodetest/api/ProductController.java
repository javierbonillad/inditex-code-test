package com.example.inditexcodetest.api;

import com.example.inditexcodetest.api.dto.ProductDTO;
import com.example.inditexcodetest.api.mapper.ApiMapper;
import com.example.inditexcodetest.api.util.GenericResponseEntity;
import com.example.inditexcodetest.application.GetProduct;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@AllArgsConstructor
@RestController
public class ProductController {

  private final ApiMapper apiMapper;
  private final GetProduct getProduct;

  @GetMapping("/products/{productId}")
  public GenericResponseEntity<ProductDTO> getProduct(@PathVariable("productId") final String productId,
                                                      @RequestParam("brandId") final String brandId,
                                                      @RequestParam("date") final Instant date) {

    return GenericResponseEntity.fromEither(
        getProduct.apply(productId, brandId, date)
            .map(apiMapper::toProductDTO)
            .map(GenericResponseEntity::withData)
            .mapLeft(GenericResponseEntity::withError));
  }
}
