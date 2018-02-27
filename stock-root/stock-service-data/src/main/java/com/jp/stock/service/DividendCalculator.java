/** */
package com.jp.stock.service;

import com.jp.stock.service.bo.StockBO;
import java.math.BigDecimal;

/**
 * Interface defining strategy to calculate the dividend.
 *
 * @author chandresh.mishra
 */
public interface DividendCalculator {

  public static final int PRECISION_SCALE = 7;

  BigDecimal calculate(StockBO stock, BigDecimal price);
}
