package com.example.inditexcodetest.application;

import com.example.inditexcodetest.domain.ProductService;
import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.DateRange;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import io.vavr.control.Either;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductTest {

  @InjectMocks
  private GetProduct getProduct;

  @Mock
  private ProductService productService;

  @Test
  void should_return_product() {
    // GIVEN
    final String productId = "12345";
    final String brandId = "1";
    final Instant date = Instant.now();

    when(productService.getProduct(any(ProductRequest.class))).thenReturn(Either.right(mockProduct(productId, brandId)));

    // WHEN
    final Either<InditexException, Product> response = getProduct.apply(productId, brandId, date);

    // THEN
    assertThat(response)
        .isNotNull()
        .isInstanceOf(Either.Right.class)
        .extracting("value")
        .isNotNull()
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("productId", productId)
        .hasFieldOrPropertyWithValue("brandId", brandId);
  }

  @Test
  void should_fail_return_product_when_something_goes_wrong() {
    // GIVEN
    final String productId = "11111";
    final String brandId = "2";
    final Instant date = Instant.now();

    when(productService.getProduct(any(ProductRequest.class))).thenReturn(Either.left(new InditexException("Exception")));

    // WHEN
    final Either<InditexException, Product> response = getProduct.apply(productId, brandId, date);

    // THEN
    assertThat(response)
        .isNotNull()
        .isInstanceOf(Either.Left.class)
        .extracting("value")
        .isNotNull()
        .isInstanceOf(InditexException.class);
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
