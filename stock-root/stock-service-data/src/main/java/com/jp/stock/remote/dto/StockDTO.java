/**
 * 
 */
package com.jp.stock.remote.dto;

import java.math.BigDecimal;

import com.jp.stock.enums.StockType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chandresh.mishra
 *
 */
@ToString
public class StockDTO {

	/** The stock symbol */
	@Getter
	@Setter
	private String stockSymbol;

	/** The last dividend value */
	@Getter
	@Setter
	private BigDecimal lastDividend;

	/** The par value. */
	@Getter
	@Setter
	private BigDecimal parValue;

	/** The price of this stock. */
	@Getter
	@Setter
	private BigDecimal price;

	/** The Type of this stock. */
	@Getter
	@Setter
	private StockType stockType;

	/** The last dividend value */
	@Getter
	@Setter
	private BigDecimal fixedDividend;
}
