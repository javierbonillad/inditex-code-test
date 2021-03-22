package com.example.inditexcodetest.infrastructure.repository;

import com.example.inditexcodetest.infrastructure.repository.model.PriceDBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceDBO, String> {

  Optional<PriceDBO> findTopByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(final String productId, final String brandId, final Instant date, final Instant sameDate);
}
