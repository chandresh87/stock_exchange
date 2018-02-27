/** */
package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import java.math.BigDecimal;

/**
 * Implement Common Dividend Calculator
 *
 * @author chandresh.mishra
 */
public class CommonDividendCalculator implements DividendCalculator {

  @Override
  public BigDecimal calculate(StockBO stock, BigDecimal price) {
    return stock.getLastDividend().divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
  }
}
