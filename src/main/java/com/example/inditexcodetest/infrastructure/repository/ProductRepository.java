package com.example.inditexcodetest.infrastructure.repository;

import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import io.vavr.control.Either;

public interface ProductRepository {

  Either<InditexException, Product> getProductUsingFilters(ProductRequest productRequest);
}
