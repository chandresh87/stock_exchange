package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.constant.StockServiceConstants;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.exception.code.BusinessErrorCode;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * StockService interface exposes the method to operate on a given Stock It calculate the dividend
 * yield, P/E Ratio, Stock Price and record trades for a given stock.
 *
 * @author chandresh.mishra
 */
public interface StockService {

  /**
   * Calculates the dividend yield for a given stock.
   *
   * @param symbol Stock symbol.
   * @param price price of Stock
   * @return A BigDecimal value which represents the dividend yield for a given stock.
   */
  public BigDecimal getDividendYield(String symbol, BigDecimal price);

  /**
   * Calculates the P/E Ratio for a given stock.
   *
   * @param symbol Stock symbol.
   * @param price price of Stock
   * @return A BigDecimal value which represents the P/E Ratio for a given stock.
   */
  public BigDecimal getPERatio(String symbol, BigDecimal price);

  /**
   * Record a trade in the Simple Stocks application.
   *
   * @param trade
   */
  public void recordTrade(TradeBO trade);

  /**
   * Gets the volume weighted stock price based on the trades in last 15 minutes.
   *
   * @param stock Stock symbol.
   * @return BigDecimal the VolumeWeightedStockPrice in the last in 15 minutes.
   */
  public BigDecimal getVolumeWeightedStockPrice(String symbol);

  /**
   * Saves the list of Stock in Gemfire Region
   *
   * @param stockList List of Stock
   */
  public void saveStockCollection(List<StockBO> stockList);

  /**
   * calculates the dividend, depending on the type of stock.
   *
   * @param stock stock object
   * @param price market price of the stock
   * @param dividendCalculator strategy to choose for calculation
   */
  default BigDecimal calculateDividend(
      StockBO stock, BigDecimal price, DividendCalculator dividendCalculator) {
    return dividendCalculator.calculate(stock, price);
  }

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
