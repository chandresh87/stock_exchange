/** */
package com.jp.stock.service.bo;

import com.jp.stock.enums.TradeIndicator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.ToString;

/**
 * A business class representation for the trade record containing the trade information, like stock
 * , time stamp, quantity of shares, buy or sell indicator and trading price.
 *
 * @author chandresh.mishra
 */
@ToString
public class TradeBO {

  /** The stock symbol */
  private String stockSymbol;

  /** The quantify of this trade. */
  private BigInteger quantity;

  /** A buy/sell trade indicator. */
  private TradeIndicator indicator;

  /** The trade price. */
  private BigDecimal price;

  /** The time stamp of this trade. */
  private LocalDateTime timestamp;

  public TradeBO(TradeBOBuilder builder) {
    this.stockSymbol = builder.stockSymbol;
    this.quantity = builder.quantity;
    this.indicator = builder.indicator;
    this.price = builder.price;
    this.timestamp = builder.timestamp;
  }

  public TradeBO() {
    //default constructor called by the Mapper.
  }

  /** Builder pattern implementation to build StockBO object. */
  public static class TradeBOBuilder {

    private String stockSymbol;
    private BigInteger quantity;
    private TradeIndicator indicator;
    private BigDecimal price;
    private LocalDateTime timestamp;

    public TradeBOBuilder() {}

    /**
     * Set stockSymbol
     *
     * @param stockSymbol stock symbol
     * @return TradeBOBuilder
     */
    public TradeBOBuilder stockSymbol(String stockSymbol) {
      this.stockSymbol = stockSymbol;
      return this;
    }

    /**
     * Set quantity
     *
     * @param quantity Trade quantity
     * @return TradeBOBuilder
     */
    public TradeBOBuilder quantity(BigInteger quantity) {
      this.quantity = quantity;
      return this;
    }

    /**
     * Set indicator
     *
     * @param indicator Trade indicator
     * @return TradeBOBuilder
     */
    public TradeBOBuilder indicator(TradeIndicator indicator) {
      this.indicator = indicator;
      return this;
    }

    /**
     * Set price
     *
     * @param price Trade price
     * @return TradeBOBuilder
     */
    public TradeBOBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    /**
     * Set price
     *
     * @param price Trade price
     * @return TradeBOBuilder
     */
    public TradeBOBuilder timeStamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * Build the TradeBO object by calling constructor in TradeBO class.
     *
     * @return TradeBO object.
     */
    public TradeBO build() {

      return new TradeBO(this);
    }
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getStockSymbol() {
    return stockSymbol;
  }

  public void setStockSymbol(String stockSymbol) {
    this.stockSymbol = stockSymbol;
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

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
