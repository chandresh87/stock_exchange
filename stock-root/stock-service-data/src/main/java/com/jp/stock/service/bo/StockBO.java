/** */
package com.jp.stock.service.bo;

import com.jp.stock.enums.StockType;
import java.math.BigDecimal;

/**
 * Stock business class to apply business logic on stock.
 *
 * @author chandresh.mishra
 */
public class StockBO {

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

  /** The fixed dividend value */
  private BigDecimal fixedDividend;

  public StockBO(StockBOBuilder builder) {
    this.fixedDividend = builder.fixedDividend;
    this.stockSymbol = builder.stockSymbol;
    this.parValue = builder.parValue;
    this.price = builder.price;
    this.stockType = builder.stockType;
    this.lastDividend = builder.lastDividend;
  }

  public StockBO() {
    //default constructor called by the Mapper.
  }

  /** Builder pattern implementation to build StockBO object. */
  public static class StockBOBuilder {

    private String stockSymbol;
    private BigDecimal lastDividend;
    private BigDecimal parValue;
    private BigDecimal price;
    private StockType stockType;
    private BigDecimal fixedDividend;

    public StockBOBuilder() {
      //default constructor
    }

    /**
     * Set stockSymbol
     *
     * @param stockSymbol stock symbol
     * @return StockBOBuilder
     */
    public StockBOBuilder stockSymbol(String stockSymbol) {
      this.stockSymbol = stockSymbol;
      return this;
    }

    /**
     * Set lastDividend
     *
     * @param lastDividend stock lastDividend
     * @return StockBOBuilder
     */
    public StockBOBuilder lastDividend(BigDecimal lastDividend) {
      this.lastDividend = lastDividend;
      return this;
    }

    /**
     * Set parValue
     *
     * @param parValue stock parValue
     * @return StockBOBuilder
     */
    public StockBOBuilder parValue(BigDecimal parValue) {
      this.parValue = parValue;
      return this;
    }

    /**
     * Set stock price
     *
     * @param price stock price
     * @return StockBOBuilder
     */
    public StockBOBuilder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    /**
     * Set stockType
     *
     * @param stockType Type of stock
     * @return StockBOBuilder
     */
    public StockBOBuilder stockType(StockType stockType) {
      this.stockType = stockType;
      return this;
    }

    /**
     * Set fixedDividend
     *
     * @param stockfixedDividend fixedDividend of stock
     * @return StockBOBuilder
     */
    public StockBOBuilder fixedDividend(BigDecimal fixedDividend) {
      this.fixedDividend = fixedDividend;
      return this;
    }

    /**
     * Build the StockBO object by calling constructor in StockBO class.
     *
     * @return StockBO object.
     */
    public StockBO build() {

      return new StockBO(this);
    }
  }

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
        .append("StockBO [stockSymbol=")
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
