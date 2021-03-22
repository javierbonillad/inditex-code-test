package com.example.inditexcodetest.infrastructure.repository.mapper;

import com.example.inditexcodetest.domain.model.Product;
import com.example.inditexcodetest.infrastructure.repository.model.PriceDBO;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  @Mapping(source = "startDate", target = "dateRange.startDate")
  @Mapping(source = "endDate", target = "dateRange.endDate")
  @Mapping(target = "amount", source = "priceDBO", qualifiedByName = "moneyMapper")
  Product toProduct(PriceDBO priceDBO);

  @Named("moneyMapper")
  default Money toMoney(final PriceDBO priceDBO) {
    if (priceDBO.getPrice() != null && priceDBO.getCurrency() != null) {
      return Money.of(priceDBO.getPrice(), priceDBO.getCurrency());
    }

    return null;
  }
}
