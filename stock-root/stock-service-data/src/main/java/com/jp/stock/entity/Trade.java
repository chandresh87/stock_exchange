/** */
package com.jp.stock.entity;

import com.jp.stock.enums.TradeIndicator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * A entity class for trade record containing the trade information, like stock , time stamp,
 * quantity of shares, buy or sell indicator and trading price.
 *
 * <p>It is used to hold data in Gemfire trade region
 *
 * @author chandresh.mishra
 */

/** Representing the Stock region in Gemfire */
@Region("Trade")
@ToString
public class Trade implements Serializable {

  private static final long serialVersionUID = 3209342518270638001L;

  /** The trade identifier used as key in trade region */
  @Id private Integer tradeIdentifier;

  /** The stock */
  private Stock stock;

  /** The time stamp of this trade. */
  private LocalDateTime tradeTimestamp;

  /** The quantify of this trade. */
  private BigInteger quantity;

  /** A buy/sell trade indicator. */
  private TradeIndicator indicator;

  /** The trade price. */
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
  public Trade(
      int tradeIdentifier,
      Stock stock,
      LocalDateTime timestamp,
      BigInteger quantity,
      TradeIndicator indicator,
      BigDecimal price) {

    this.tradeIdentifier = tradeIdentifier;
    this.stock = stock;
    this.tradeTimestamp = timestamp;
    this.quantity = quantity;
    this.indicator = indicator;
    this.price = price;
  }

  public Trade() {}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((stock == null) ? 0 : stock.hashCode());
    result = prime * result + ((tradeTimestamp == null) ? 0 : tradeTimestamp.hashCode());
    result = prime * result + tradeIdentifier;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Trade)) return false;
    Trade other = (Trade) obj;
    if (stock == null) {
      if (other.stock != null) return false;
    } else if (!stock.equals(other.stock)) return false;
    if (tradeTimestamp == null) {
      if (other.tradeTimestamp != null) return false;
    } else if (!tradeTimestamp.equals(other.tradeTimestamp)) return false;
    if (tradeIdentifier != other.tradeIdentifier) return false;
    return true;
  }

  public Integer getTradeIdentifier() {
    return tradeIdentifier;
  }

  public void setTradeIdentifier(Integer tradeIdentifier) {
    this.tradeIdentifier = tradeIdentifier;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public LocalDateTime getTradeTimestamp() {
    return tradeTimestamp;
  }

  public void setTradeTimestamp(LocalDateTime tradeTimestamp) {
    this.tradeTimestamp = tradeTimestamp;
  }

  public BigInteger getQuantity() {
    return quantity;
  }

  public void setQuantity(BigInteger quantity) {
    this.quantity = quantity;
  }

  public TradeIndicator getIndicator() {
    return indicator;
  }

  public void setIndicator(TradeIndicator indicator) {
    this.indicator = indicator;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
