package com.example.inditexcodetest.infrastructure;

import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.exception.ProductNotFoundException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import com.example.inditexcodetest.infrastructure.repository.PriceJpaRepository;
import com.example.inditexcodetest.infrastructure.repository.ProductRepositoryImpl;
import com.example.inditexcodetest.infrastructure.repository.mapper.PriceMapper;
import com.example.inditexcodetest.infrastructure.repository.mapper.PriceMapperImpl;
import com.example.inditexcodetest.infrastructure.repository.model.PriceDBO;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRespositoryImplTest {

  @InjectMocks
  private ProductRepositoryImpl productRepository;

  @Mock
  private PriceJpaRepository priceJpaRepository;

  @Spy
  private PriceMapper priceMapper = new PriceMapperImpl();

  @Test
  void should_get_product() {
    // GIVEN
    final ProductRequest productRequest = ProductRequest.builder()
        .date(Instant.now())
        .brandId("1")
        .productId("1")
        .build();

    when(priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(anyString(), anyString(), any(Instant.class), any(Instant.class)))
        .thenReturn(Optional.of(mockPriceDbo(productRequest.getBrandId(), productRequest.getProductId())));

    // WHEN
    final Either<InditexException, Product> either = productRepository.getProductUsingFilters(productRequest);

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
  void should_get_product_not_found_when_there_are_no_matching_products() {
    // GIVEN
    final ProductRequest productRequest = ProductRequest.builder()
        .date(Instant.now())
        .brandId("1")
        .productId("1")
        .build();

    when(priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(anyString(), anyString(), any(Instant.class), any(Instant.class)))
        .thenReturn(Optional.empty());

    // WHEN
    final Either<InditexException, Product> either = productRepository.getProductUsingFilters(productRequest);

    // THEN
    assertThat(either)
        .isNotNull()
        .isInstanceOf(Either.Left.class)
        .extracting("value")
        .isNotNull()
        .isInstanceOf(ProductNotFoundException.class);
  }

  @Test
  void should_fail_get_product_when_something_goes_wrong() {
    // GIVEN
    final ProductRequest productRequest = ProductRequest.builder()
        .date(Instant.now())
        .brandId("1")
        .productId("1")
        .build();

    when(priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(anyString(), anyString(), any(Instant.class), any(Instant.class)))
        .thenThrow(new RuntimeException("Exception"));

    // WHEN
    final Either<InditexException, Product> either = productRepository.getProductUsingFilters(productRequest);

    // THEN
    assertThat(either)
        .isNotNull()
        .isInstanceOf(Either.Left.class)
        .extracting("value")
        .isNotNull()
        .isInstanceOf(InditexException.class);
  }

  private PriceDBO mockPriceDbo(final String brandId, final String productId) {
    PriceDBO priceDBO = new PriceDBO();

    priceDBO.setBrandId(brandId);
    priceDBO.setProductId(productId);
    priceDBO.setPrice(BigDecimal.TEN);
    priceDBO.setPriceList("1");
    priceDBO.setCurrency("EUR");
    priceDBO.setEndDate(Instant.now().plusSeconds(30));
    priceDBO.setId("1");
    priceDBO.setPriority(1);
    priceDBO.setStartDate(Instant.now().minusSeconds(30));

    return priceDBO;
  }
}
