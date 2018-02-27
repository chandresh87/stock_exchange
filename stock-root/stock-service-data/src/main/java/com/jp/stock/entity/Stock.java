/**
 * 
 */
package com.jp.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;

import com.jp.stock.enums.StockType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Stock class to hold the stock data in Gemfire.
 *
 * @author chandresh.mishra
 *
 */

/** Representing the Stock region in Gemfire */
@Region("Stock")
@ToString
public class Stock implements Serializable {

	private static final long serialVersionUID = 3209342518270638000L;

	/** The symbol of the stock used as key in gemfire region */
	@Id
	@Getter
	@Setter
	private String symbol;

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

	/**
	 * @param symbol
	 * @param lastDividend
	 * @param parValue
	 * @param price
	 * @param stockType
	 * @param fixedDividend
	 */

	/** This constructor will be used for persisting data in Gemfire */
	@PersistenceConstructor
	public Stock(String symbol, BigDecimal lastDividend, BigDecimal parValue,
			BigDecimal price, StockType stockType, BigDecimal fixedDividend) {
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.price = price;
		this.stockType = stockType;
		this.fixedDividend = fixedDividend;

	}

	public Stock() {

	}	
}
