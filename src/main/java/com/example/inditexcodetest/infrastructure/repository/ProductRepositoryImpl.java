package com.example.inditexcodetest.infrastructure.repository;

import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.exception.ProductNotFoundException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import com.example.inditexcodetest.infrastructure.repository.mapper.PriceMapper;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final PriceJpaRepository priceJpaRepository;
  private final PriceMapper priceMapper;

  @Override
  public Either<InditexException, Product> getProductUsingFilters(final ProductRequest productRequest) {
    return Try.of(() -> priceJpaRepository.findTopByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(productRequest.getProductId(), productRequest.getBrandId(), productRequest.getDate(), productRequest.getDate())
        .map(priceMapper::toProduct)
        .orElseThrow(() -> new ProductNotFoundException(productRequest.getProductId())))
        .toEither()
        .mapLeft(this::handleDomainExceptions);
  }

  private InditexException handleDomainExceptions(final Throwable throwable) {
    return Match(throwable)
        .of(Case($(instanceOf(InditexException.class)), e -> e),
            Case($(), InditexException::new));
  }
}
