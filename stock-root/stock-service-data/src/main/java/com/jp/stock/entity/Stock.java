package com.jp.stock.entity;

import com.jp.stock.enums.StockType;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * Stock entity to hold the stock data in Gemfire.
 *
 * @author chandresh.mishra
 */

/** Representing the Stock region in Gemfire */
@Region("Stock")
public class Stock implements Serializable {

  private static final long serialVersionUID = 3209342518270638000L;

  /** The symbol of the stock used as key in gemfire region */
  @Id private String symbol;

  /** The last dividend value */
  private BigDecimal lastDividend;

  /** The par value. */
  private BigDecimal parValue;

  /** The Type of this stock. */
  private StockType stockType;

  /** The last dividend value */
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
  public Stock(
      String symbol,
      BigDecimal lastDividend,
      BigDecimal parValue,
      StockType stockType,
      BigDecimal fixedDividend) {
    this.symbol = symbol;
    this.lastDividend = lastDividend;
    this.parValue = parValue;
    this.stockType = stockType;
    this.fixedDividend = fixedDividend;
  }

  public Stock() {}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((stockType == null) ? 0 : stockType.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Stock)) return false;
    Stock other = (Stock) obj;
    if (stockType != other.stockType) return false;
    if (symbol == null) {
      if (other.symbol != null) return false;
    } else if (!symbol.equals(other.symbol)) return false;
    return true;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
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
        .append("Stock [symbol=")
        .append(symbol)
        .append(", lastDividend=")
        .append(lastDividend)
        .append(", parValue=")
        .append(parValue)
        .append(", stockType=")
        .append(stockType)
        .append(", fixedDividend=")
        .append(fixedDividend)
        .append("]");
    return builder.toString();
  }
}
