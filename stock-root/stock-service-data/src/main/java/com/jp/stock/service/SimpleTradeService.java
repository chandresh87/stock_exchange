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

/** It provides the Implementation for the TradeService Interface */

/** Register it as a bean in Spring context */
@Service
public class SimpleTradeService implements TradeService {

  /** Autowired Trade database service */
  @Autowired TradeDao tradeDao;

  /** Autowired Trade mapper */
  @Autowired TradeMapper tradeMapper;

  private static final Logger logger = LogManager.getLogger();

  /**
   * Calculates the GBCE all share index value based on the prices of all the stocks in the market
   * service.
   *
   * @return the GBCE all share index value.
   */
  public BigDecimal calculateGBCEAllShareIndex() {

    logger.traceEntry("Calculating GBCE All Share Index:");

    // Fetching all the trade from trade region in Gemfire
    List<Trade> tradeCollection = tradeDao.findAll();

    //Throwing exception if there is no trade in  gemfire
    if (tradeCollection.isEmpty())
      throw new StockMarketServiceException(
          BusinessErrorCode.JP001B.getId(),
          BusinessErrorCode.JP001B.getDescription(),
          new Throwable(StockServiceConstants.TRADE_NOT_FOUND));

    //Mapping trade List to TradeBO list
    List<TradeBO> tradeBOList = tradeMapper.tradeListToTradeBOList(tradeCollection);

    // Calculating  GBSE
    double accumulate =
        tradeBOList
            .parallelStream()
            .map(tradeRecord -> tradeRecord.getPrice().doubleValue())
            .reduce(1.0, (x, y) -> x * y);

    double geometricMean = Math.pow(accumulate, (double) (1.0 / tradeBOList.size()));
    BigDecimal geometricMeanResult = BigDecimal.valueOf(geometricMean);
    geometricMeanResult.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    logger.traceExit("Result: geometricMeanResult - {}", geometricMeanResult);
    return geometricMeanResult;
  }
}
