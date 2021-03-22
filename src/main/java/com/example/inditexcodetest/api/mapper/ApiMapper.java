package com.example.inditexcodetest.api.mapper;

import com.example.inditexcodetest.api.dto.DateRangeDTO;
import com.example.inditexcodetest.api.dto.MoneyDTO;
import com.example.inditexcodetest.api.dto.ProductDTO;
import com.example.inditexcodetest.domain.model.DateRange;
import com.example.inditexcodetest.domain.model.Product;
import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiMapper {

  ProductDTO toProductDTO(Product product);

  DateRangeDTO toDateRangeDTO(DateRange dateRange);

  default MoneyDTO fromMoney(Money money) {
    if (money != null) {
      return MoneyDTO.builder()
          .amount(money.getNumberStripped().setScale(2).toPlainString())
          .currency(money.getCurrency().getCurrencyCode())
          .build();
    }

    return null;
  }
}
