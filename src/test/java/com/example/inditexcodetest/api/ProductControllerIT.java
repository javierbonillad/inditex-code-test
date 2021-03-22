package com.example.inditexcodetest.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerIT extends AbstractRestTest {

  private static Stream<Arguments> productsQueryArguments() {
    return Stream.of(
        arguments("35455", "1", "2020-06-14T10:00:00Z", buildResultMap("35455", "1", "1",
            "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", "35.50", "EUR")),
        arguments("35455", "1", "2020-06-14T16:00:00Z", buildResultMap("35455", "1", "2",
            "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", "25.45", "EUR")),
        arguments("35455", "1", "2020-06-14T21:00:00Z", buildResultMap("35455", "1", "1",
            "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", "35.50", "EUR")),
        arguments("35455", "1", "2020-06-15T10:00:00Z", buildResultMap("35455", "1", "3",
            "2020-06-15T00:00:00Z", "2020-06-15T11:00:00Z", "30.50", "EUR")),
        arguments("35455", "1", "2020-06-16T21:00:00Z", buildResultMap("35455", "1", "4",
            "2020-06-15T16:00:00Z", "2020-12-31T23:59:59Z", "38.95", "EUR"))
    );
  }

  private static Map<String, String> buildResultMap(final String productId, final String brandId, final String priceList,
                                                    final String startDate, final String endDate, final String amount, final String currency) {
    return Map.of("productId", productId,
        "brandId", brandId,
        "priceList", priceList,
        "startDate", startDate,
        "endDate", endDate,
        "amount", amount,
        "currency", currency);
  }

  @MethodSource("productsQueryArguments")
  @ParameterizedTest(name = "Given a product with ID {0}, a brand with ID {1} and date to be applied {2}, when the product request is executed, then the product is: {3}")
  void should_return_correct_product(final String productId, final String brandId, final String date, final Map<String, Object> productResult) throws Exception {
    // GIVEN Arguments received by params

    // WHEN
    this.mockMvc.perform(get("/products/{productId}", productId)
        .queryParam("brandId", brandId)
        .queryParam("date", date))

        // THEN
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("data.productId").value(productResult.get("productId")))
        .andExpect(jsonPath("data.brandId").value(productResult.get("brandId")))
        .andExpect(jsonPath("data.priceList").value(productResult.get("priceList")))
        .andExpect(jsonPath("data.dateRange.startDate").value(productResult.get("startDate")))
        .andExpect(jsonPath("data.dateRange.endDate").value(productResult.get("endDate")))
        .andExpect(jsonPath("data.amount.amount").value(productResult.get("amount")))
        .andExpect(jsonPath("data.amount.currency").value(productResult.get("currency")));
  }

  @Test
  void should_get_bad_request_when_some_param_not_present() throws Exception {
    // GIVEN
    final String productId = "35455";
    final String brandId = null;
    final String date = Instant.now().toString();

    // WHEN
    this.mockMvc.perform(get("/products/{productId}", productId)
        .queryParam("brandId", brandId)
        .queryParam("date", date))

        // THEN
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_get_not_found_given_a_non_existing_product() throws Exception {
    // GIVEN
    final String productId = "12345";
    final String brandId = "1";
    final String date = "2020-06-14T10:00:00.00Z";

    // WHEN
    this.mockMvc.perform(get("/products/{productId}", productId)
        .queryParam("brandId", brandId)
        .queryParam("date", date))

        // THEN
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("error.code").exists())
        .andExpect(jsonPath("error.message").exists());
  }
}
