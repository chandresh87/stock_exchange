package com.jp.stock.service;

import com.jp.stock.service.constant.StockServiceConstants;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.exception.code.BusinessErrorCode;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * It exposes the method for calculation of GBCE All Share Index
 *
 * @author chandresh.mishra
 */
public interface TradeService {

  /**
   * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks.
   *
   * @return BigDecimal geometric mean of prices for all stocks
   */
  public BigDecimal calculateGBCEAllShareIndex();

  /**
   * Utility method to validates the given value is a positive.
   *
   * @param value the value to be checked
   */
  public static void checkPositive(BigDecimal value) {

    Optional.ofNullable(value)
        .filter(decimalValue -> BigDecimal.ZERO.compareTo(decimalValue) < 0)
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP003B.getId(),
                    BusinessErrorCode.JP003B.getDescription(),
                    new Throwable(StockServiceConstants.FOUND_NON_POSITIVE_VALUE + value)));
  }
}
