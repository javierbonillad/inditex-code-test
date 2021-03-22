package com.example.inditexcodetest.domain;

import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import io.vavr.control.Either;

public interface ProductService {

  Either<InditexException, Product> getProduct(ProductRequest productRequest);
}
