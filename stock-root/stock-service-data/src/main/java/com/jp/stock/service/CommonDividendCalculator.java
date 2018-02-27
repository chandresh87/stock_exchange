/**
 * 
 */
package com.jp.stock.service;

import java.math.BigDecimal;

import com.jp.stock.service.bo.StockBO;

/**
 * Implement Common Dividend Calculator
 * @author chandresh.mishra
 *
 */
public class CommonDividendCalculator implements DividendCalculator{

	@Override
	public BigDecimal calculate(StockBO stock, BigDecimal price) {
		return stock.getLastDividend().divide(price, PRECISION_SCALE,
				BigDecimal.ROUND_HALF_EVEN);
	} 

}
