/**
 * 
 */
package com.jp.stock.service;

import java.math.BigDecimal;

import com.jp.stock.service.bo.StockBO;

/**
 * Interface defining strategy to calculate the dividend.
 *  
 * @author chandresh.mishra
 *
 */
public interface DividendCalculator {

 public static final int PRECISION_SCALE = 7;
	
 BigDecimal calculate(StockBO stock, BigDecimal price); 
}
