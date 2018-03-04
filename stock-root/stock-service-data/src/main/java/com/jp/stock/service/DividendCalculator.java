package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import java.math.BigDecimal;

/**
 * Interface defining different strategy to calculate the dividend.
 *
 * @author chandresh.mishra
 */
public interface DividendCalculator {

  public static final int PRECISION_SCALE = 7;

  /**
   * Calculates the dividend yield for a given stock.
   *
   * @param symbol Stock symbol.
   * @param price price of Stock
   * @return A BigDecimal value which represents the dividend yield for a given stock.
   */
  BigDecimal calculate(StockBO stock, BigDecimal price);
}
