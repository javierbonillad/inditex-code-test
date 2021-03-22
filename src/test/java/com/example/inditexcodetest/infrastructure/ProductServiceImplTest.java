package com.example.inditexcodetest.infrastructure;

import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.DateRange;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import com.example.inditexcodetest.infrastructure.repository.ProductRepository;
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
class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  @Test
  void should_get_product() {
    // GIVEN
    final ProductRequest productRequest = ProductRequest.builder()
        .date(Instant.now())
        .brandId("1")
        .productId("1")
        .build();

    when(productRepository.getProductUsingFilters(any(ProductRequest.class))).thenReturn(Either.right(mockProduct(productRequest.getProductId(), productRequest.getBrandId())));

    // WHEN
    final Either<InditexException, Product> either = productService.getProduct(productRequest);

    // THEN
    assertThat(either)
        .isNotNull()
        .isInstanceOf(Either.Right.class)
        .extracting("value")
        .isNotNull()
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("brandId", productRequest.getBrandId())
        .hasFieldOrPropertyWithValue("productId", productRequest.getProductId());
  }

  @Test
  void should_fail_get_product_when_something_goes_wrong() {
    // GIVEN
    final ProductRequest productRequest = ProductRequest.builder()
        .date(Instant.now())
        .brandId("1")
        .productId("1")
        .build();

    when(productRepository.getProductUsingFilters(any(ProductRequest.class))).thenReturn(Either.left(new InditexException("Exception")));

    // WHEN
    final Either<InditexException, Product> either = productService.getProduct(productRequest);

    // THEN
    assertThat(either)
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
