package com.jp.stock.service;

import com.jp.stock.dao.TradeDao;
import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.constant.StockServiceConstants;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.exception.code.BusinessErrorCode;
import com.jp.stock.service.mapper.TradeMapper;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** It provides the Implementation for the TradeQuery Interface */

/** Register it as a bean in Spring context */
@Service
public class SimpleTradeService implements TradeService {

  private static final int PRECISION_SCALE = 7;

  /** Autowired Trade database service */
  @Autowired TradeDao tradeDao;

  @Autowired TradeMapper tradeMapper;

  private static final Logger logger = LogManager.getLogger();

  /**
   * Calculates the GBCE all share index value based on the prices of all the stocks in the market
   * service.
   *
   * @return the GBCE all share index value.
   */
  public BigDecimal calculateGBCEAllShareIndex() {

    logger.info("Calculating GBCE All Share Index:");

    // Fetching all the trade from trade region in Gemfire
    List<Trade> tradeCollection = tradeDao.findAll();

    if (tradeCollection.isEmpty())
      throw new StockMarketServiceException(
          BusinessErrorCode.JP001B.getId(),
          BusinessErrorCode.JP001B.getDescription(),
          new Throwable(StockServiceConstants.TRADE_NOT_FOUND));

    List<TradeBO> tradeBOList = tradeMapper.tradeListToTradeBOList(tradeCollection);

    tradeCollection = null;

    BigDecimal accumulate = BigDecimal.ONE;

    accumulate =
        tradeBOList
            .parallelStream()
            .map(tradeRecord -> tradeRecord.getPrice())
            .reduce(BigDecimal.ONE, BigDecimal::multiply);

    /*for (Trade tradeRecord : tradeCollection) {

    	BigDecimal price = tradeRecord.getPrice();
    	checkPositive(price);
    	accumulate = accumulate.multiply(price);
    	price = null;
    }*/

    int n = tradeCollection.size();
    BigDecimal x = accumulate.divide(accumulate, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal temp = BigDecimal.ZERO;
    BigDecimal e = new BigDecimal("0.1");

    do {
      temp = x;
      x =
          x.add(
              accumulate
                  .subtract(x.pow(n))
                  .divide(
                      new BigDecimal(n).multiply(x.pow(n - 1)),
                      PRECISION_SCALE,
                      BigDecimal.ROUND_HALF_EVEN));
    } while (x.subtract(temp).abs().compareTo(e) > 0);

    return x.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }
}
