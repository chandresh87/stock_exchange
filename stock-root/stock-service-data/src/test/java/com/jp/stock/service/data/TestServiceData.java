/** */
package com.jp.stock.service.data;

import com.jp.stock.entity.Stock;
import com.jp.stock.entity.Trade;
import com.jp.stock.enums.StockType;
import com.jp.stock.enums.TradeIndicator;
import com.jp.stock.service.bo.StockBO;
import com.jp.stock.service.bo.StockBO.StockBOBuilder;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.bo.TradeBO.TradeBOBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

/** @author chandresh.mishra */
public class TestServiceData {

  public static Optional<Stock> getCommonStock() {
    return Optional.of(
        new Stock(
            "TEA", new BigDecimal(0), new BigDecimal(100), StockType.COMMON, new BigDecimal(0)));
  }

  public static Optional<Stock> getPreferredStock() {
    return Optional.of(
        new Stock(
            "GIN", new BigDecimal(8), new BigDecimal(100), StockType.PREFERRED, new BigDecimal(2)));
  }

  public static StockBOBuilder getCommonStockBO() {
    return new StockBO.StockBOBuilder()
        .stockSymbol("TEA")
        .lastDividend(new BigDecimal(0))
        .parValue(new BigDecimal(100))
        .stockType(StockType.COMMON)
        .fixedDividend(new BigDecimal(0));
  }

  public static StockBOBuilder getPreferredStockBO() {
    return new StockBO.StockBOBuilder()
        .stockSymbol("GIN")
        .lastDividend(new BigDecimal(8))
        .parValue(new BigDecimal(100))
        .stockType(StockType.PREFERRED)
        .fixedDividend(new BigDecimal(2));
  }

  public static TradeBOBuilder getTradeBO() {
    return new TradeBO.TradeBOBuilder()
        .indicator(TradeIndicator.BUY)
        .price(new BigDecimal(100))
        .quantity(new BigInteger("150"))
        .stockSymbol("TEA");
  }

  public static Trade getTrade() {
    Trade trade = new Trade();
    trade.setIndicator(TradeIndicator.BUY);
    trade.setPrice(new BigDecimal(100));
    trade.setQuantity(new BigInteger("150"));
    trade.setTradeTimestamp(LocalDateTime.now());

    return trade;
  }
}
