/** */
package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import java.math.BigDecimal;

/**
 * Implement Preferred Dividend Calculator
 *
 * @author chandresh.mishra
 */
public class PreferredDividendCalculator implements DividendCalculator {

  @Override
  public BigDecimal calculate(StockBO stock, BigDecimal price) {

    return stock
        .getFixedDividend()
        .multiply(stock.getParValue())
        .divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
  }
}
