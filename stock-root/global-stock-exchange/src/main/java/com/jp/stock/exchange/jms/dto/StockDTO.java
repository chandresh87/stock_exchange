
package com.jp.stock.exchange.jms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class act as a data transfer object for the remote operation of loading the Global Beverage
 * Corporation Exchange data into the simple stock application.
 *
 * @author chandresh.mishra
 */
public class StockDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The stock symbol */
  private String stockSymbol;

  /** The last dividend value */
  private BigDecimal lastDividend;

  /** The par value. */
  private BigDecimal parValue;

  /** The price of this stock. */
  private BigDecimal price;

  /** The Type of this stock. */
  private StockType stockType;

  /** The last dividend value */
  private BigDecimal fixedDividend;

  public String getStockSymbol() {
    return stockSymbol;
  }

  public void setStockSymbol(String stockSymbol) {
    this.stockSymbol = stockSymbol;
  }

  public BigDecimal getLastDividend() {
    return lastDividend;
  }

  public void setLastDividend(BigDecimal lastDividend) {
    this.lastDividend = lastDividend;
  }

  public BigDecimal getParValue() {
    return parValue;
  }

  public void setParValue(BigDecimal parValue) {
    this.parValue = parValue;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public StockType getStockType() {
    return stockType;
  }

  public void setStockType(StockType stockType) {
    this.stockType = stockType;
  }

  public BigDecimal getFixedDividend() {
    return fixedDividend;
  }

  public void setFixedDividend(BigDecimal fixedDividend) {
    this.fixedDividend = fixedDividend;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("StockDTO [stockSymbol=")
        .append(stockSymbol)
        .append(", lastDividend=")
        .append(lastDividend)
        .append(", parValue=")
        .append(parValue)
        .append(", price=")
        .append(price)
        .append(", stockType=")
        .append(stockType)
        .append(", fixedDividend=")
        .append(fixedDividend)
        .append("]");
    return builder.toString();
  }
}
