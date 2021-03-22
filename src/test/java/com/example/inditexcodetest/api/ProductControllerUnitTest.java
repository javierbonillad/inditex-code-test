package com.example.inditexcodetest.api;

import com.example.inditexcodetest.api.dto.ProductDTO;
import com.example.inditexcodetest.api.mapper.ApiMapper;
import com.example.inditexcodetest.api.mapper.ApiMapperImpl;
import com.example.inditexcodetest.api.util.GenericResponseEntity;
import com.example.inditexcodetest.application.GetProduct;
import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.exception.ProductNotFoundException;
import com.example.inditexcodetest.domain.model.DateRange;
import com.example.inditexcodetest.domain.model.Product;
import io.vavr.control.Either;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerUnitTest {

  @InjectMocks
  private ProductController productController;

  @Mock
  private GetProduct getProduct;

  @Spy
  private ApiMapper apiMapper = new ApiMapperImpl();

  @Test
  void should_get_product() {
    // GIVEN
    final String productId = "1";
    final String brandId = "2";
    final Instant date = Instant.parse("2020-06-14T10:00:00.00Z");

    when(getProduct.apply(anyString(), anyString(), any(Instant.class))).thenReturn(Either.right(mockProduct(productId, brandId)));

    // WHEN
    final GenericResponseEntity<ProductDTO> response = productController.getProduct(productId, brandId, date);

    // THEN
    assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("status", HttpStatus.OK)
        .extracting("body")
        .isNotNull()
        .extracting("data")
        .isNotNull()
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("productId", productId)
        .hasFieldOrPropertyWithValue("brandId", brandId);

    verify(getProduct).apply(anyString(), anyString(), any(Instant.class));
  }

  @Test
  void should_get_not_found_when_no_products_were_found() {
    // GIVEN
    final String productId = "123456";
    final String brandId = "2";
    final Instant date = Instant.parse("2020-06-14T10:00:00.00Z");

    when(getProduct.apply(anyString(), anyString(), any(Instant.class))).thenReturn(Either.left(new ProductNotFoundException("Exception")));

    // WHEN
    final GenericResponseEntity<ProductDTO> response = productController.getProduct(productId, brandId, date);

    // THEN
    assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
        .extracting("body")
        .isNotNull()
        .extracting("error")
        .isNotNull()
        .hasFieldOrProperty("code")
        .hasFieldOrProperty("message");

    verify(getProduct).apply(anyString(), anyString(), any(Instant.class));
  }

  @Test
  void should_fail_get_product_when_something_goes_wrong() {
    // GIVEN
    final String productId = "123456";
    final String brandId = "2";
    final Instant date = Instant.parse("2020-06-14T10:00:00.00Z");

    when(getProduct.apply(anyString(), anyString(), any(Instant.class))).thenReturn(Either.left(new InditexException("Exception")));

    // WHEN
    final GenericResponseEntity<ProductDTO> response = productController.getProduct(productId, brandId, date);

    // THEN
    assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("status", HttpStatus.INTERNAL_SERVER_ERROR)
        .extracting("body")
        .isNotNull()
        .extracting("error")
        .isNotNull()
        .hasFieldOrProperty("code")
        .hasFieldOrProperty("message");

    verify(getProduct).apply(anyString(), anyString(), any(Instant.class));
  }

  private Product mockProduct(final String productId, final String brandId) {
    return Product.builder()
        .productId(productId)
        .brandId(brandId)
        .priceList("1")
        .dateRange(DateRange.builder()
            .startDate(Instant.now().minusSeconds(30))
            .endDate(Instant.now().plusSeconds(30))
            .build())
        .amount(Money.of(BigDecimal.TEN, "EUR"))
        .build();
  }
}
