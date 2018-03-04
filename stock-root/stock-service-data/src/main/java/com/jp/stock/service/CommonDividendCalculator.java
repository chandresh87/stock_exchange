package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import java.math.BigDecimal;

/**
 * Implement Common Dividend Calculator strategy
 *
 * @author chandresh.mishra
 */
public class CommonDividendCalculator implements DividendCalculator {

  /**
   * calculate the dividend for the common stock
   *
   * @see com.jp.stock.service.DividendCalculator#calculate(com.jp.stock.service.bo.StockBO,
   *     java.math.BigDecimal)
   */
  @Override
  public BigDecimal calculate(StockBO stock, BigDecimal price) {
    return stock.getLastDividend().divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
  }
}
