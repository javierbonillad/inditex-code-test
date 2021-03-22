package com.example.inditexcodetest.infrastructure;

import com.example.inditexcodetest.domain.ProductService;
import com.example.inditexcodetest.domain.exception.InditexException;
import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.domain.model.ProductRequest;
import com.example.inditexcodetest.infrastructure.repository.ProductRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Override
  public Either<InditexException, Product> getProduct(final ProductRequest productRequest) {
    return productRepository.getProductUsingFilters(productRequest);
  }
}
