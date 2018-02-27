/** */
package com.jp.stock.remote.dto;

import com.jp.stock.enums.StockType;
import java.math.BigDecimal;
import lombok.ToString;

/** @author chandresh.mishra */
@ToString
public class StockDTO {

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
}
