/** */
package com.jp.stock.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jp.stock.api.constants.StockAPIConstant;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.validation.constraints.NotNull;
import lombok.ToString;

/**
 * Trade model to hold the trade data.
 *
 * @author chandresh.mishra
 */
@ToString
public class TradeModel {

  /** The stock symbol */
  @ApiModelProperty(value = "The stock symbol", example = "TEA", required = true)
  @JsonProperty(value = "stock_symbol")
  private String stockSymbol;

  /** The quantify of share. */
  @ApiModelProperty(value = "The quantify of share", example = "110", required = true)
  // @Pattern(regexp = "^[\\d]*[\\.]?[\\d]*$", message = StockAPIConstant.INVALID_NUMBER)
  @JsonProperty(value = "stock_quantity")
  private BigInteger quantity;

  /** A buy/sell trade indicator. */
  @ApiModelProperty(
    value = "A buy/sell trade indicator",
    required = true,
    allowableValues = "BUY,SELL"
  )
  @NotNull(message = StockAPIConstant.ERROR_NOT_NULL)
  @JsonProperty(value = "trade_indicator")
  private TradeIndicator indicator;

  /** The trade price. */
  @ApiModelProperty(value = "The trade price", example = "123.00", required = true)
  //@Pattern(regexp = "^[\\d]*[\\.]?[\\d]*$", message = StockAPIConstant.INVALID_NUMBER)
  @JsonProperty(value = "trade_price")
  private BigDecimal price;

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
