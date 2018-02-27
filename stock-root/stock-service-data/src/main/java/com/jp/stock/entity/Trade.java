/**
 * 
 */
package com.jp.stock.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;

import com.jp.stock.enums.TradeIndicator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * A entity class for trade record containing the trade information, like stock ,
 * time stamp, quantity of shares, buy or sell indicator and trading price.
 * 
 * It is used to hold data in Gemfire trade region
 * 
 * @author chandresh.mishra
 */

/** Representing the Stock region in Gemfire */
@Region("Trade")
@ToString
public class Trade implements Serializable {

	private static final long serialVersionUID = 3209342518270638001L;

	/** The trade identifier used as key in trade region */
	@Id
	@Getter
	@Setter
	private int tradeIdentifier;

	/** The stock */
	@Getter
	@Setter
	private Stock stock;

	/** The time stamp of this trade. */
	@Getter
	@Setter
	private LocalDateTime timestamp;

	
	/** The quantify of this trade. */
	@Getter
	@Setter
	private BigInteger quantity;

	/** A buy/sell trade indicator. */
	@Getter
	@Setter
	private TradeIndicator indicator;

	/** The trade price. */
	@Getter
	@Setter
	private BigDecimal price;

	/**
	 * This constructor will be used for persisting data in Gemfire.
	 * 
	 * @param tradeIdentifier
	 * @param stock
	 * @param timestamp
	 * @param quantity
	 * @param indicator
	 * @param price
	 */

	@PersistenceConstructor
	public Trade(int tradeIdentifier, Stock stock, LocalDateTime timestamp,
			BigInteger quantity, TradeIndicator indicator, BigDecimal price) {

		this.tradeIdentifier = tradeIdentifier;
		this.stock = stock;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.indicator = indicator;
		this.price = price;
	}

	public Trade() {}

}
