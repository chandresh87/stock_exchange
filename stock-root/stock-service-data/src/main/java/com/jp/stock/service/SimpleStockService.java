/** */
package com.jp.stock.service;

import com.jp.stock.dao.StockDao;
import com.jp.stock.dao.TradeDao;
import com.jp.stock.entity.Stock;
import com.jp.stock.entity.Trade;
import com.jp.stock.enums.StockType;
import com.jp.stock.service.bo.StockBO;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.constant.StockServiceConstants;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.exception.code.BusinessErrorCode;
import com.jp.stock.service.mapper.StockMapper;
import com.jp.stock.service.mapper.TradeMapper;
import com.jp.stock.service.util.SequenceGenerator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * It provides the Implementation for the StockService Interface
 *
 * @author chandresh.mishra
 */
/** Register it as a bean in Spring context */
@Service
public class SimpleStockService implements StockService {

  /** Autowired Stock database service */
  @Autowired private StockDao stockDao;

  /** Autowired Trade database service */
  @Autowired private TradeDao tradeDao;

  /** Autowired unique key generation service */
  @Autowired private SequenceGenerator generateUniqueSequence;

  /** Autowired trade mapper */
  @Autowired private TradeMapper tradeMapper;

  /** Autowired stock mapper */
  @Autowired private StockMapper stockMapper;

  private static final Logger logger = LogManager.getLogger();

  /** The precision scale in the calculation. */
  private static final int PRECISION_SCALE = 7;

  /**
   * Calculates the dividend yield of the given stock based on the given price. The result precision
   * scale is 3 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock to be calculated
   * @param price the market price
   * @return dividend yield of the given stock
   */
  @Override
  public BigDecimal getDividendYield(String symbol, BigDecimal price) {

    logger.traceEntry("parameters : symbol {} price {}", symbol, price);

    // Get the valid stock from the Gemfire Memory
    Stock stock = getValidStock(symbol);
    // Check price is valid or not
    StockService.checkPositive(price);

    // Mapping stock entity to stock BO
    StockBO stockBO = stockMapper.stockToStockBO(stock);

    BigDecimal result = BigDecimal.ZERO;

    // calculation for common stock
    if (stock.getStockType() == StockType.COMMON) {
      result = calculateDividend(stockBO, price, new CommonDividendCalculator());
    }
    // calculation for Preferred stock
    else if (stock.getStockType() == StockType.PREFERRED) {
      result = calculateDividend(stockBO, price, new PreferredDividendCalculator());
    }
    logger.traceExit("Result: dividend - {}", result);
    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Calculates the P/E Ratio of the given stock based on the given price. The result precision
   * scale is 3 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the stock symbol to be calculated
   * @param price the trade price
   * @return P/E Ratio of the given stock
   */
  @Override
  public BigDecimal getPERatio(String symbol, BigDecimal price) {

    logger.traceEntry("parameters : symbol {} price {}", symbol, price);
    // calculating the dividend for the Stock
    BigDecimal dividend = getDividendYield(symbol, price);

    //Throws exception if dividend is zero.
    Optional.ofNullable(dividend)
        .filter(decimalValue -> BigDecimal.ZERO.compareTo(decimalValue) < 0)
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP003B.getId(),
                    BusinessErrorCode.JP003B.getDescription(),
                    new Throwable(StockServiceConstants.ILLEGAL_VALUE_OF_DIVIDEND + symbol)));

    BigDecimal peRatio = price.divide(dividend, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    logger.traceExit("Result: peRatio - {}", peRatio);
    return peRatio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Records a trade, with time stamp, quantity of shares, buy or sell indicator and traded price.
   *
   * @param tradeBO trade business object
   */
  @Override
  public void recordTrade(TradeBO tradeBO) {

    logger.traceEntry("parameters : tradeBO {} ", tradeBO);

    // Checking for blank and null values
    Optional.ofNullable(tradeBO)
        .filter(validTrade -> StringUtils.isNotBlank(validTrade.getStockSymbol()))
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP001B.getId(),
                    BusinessErrorCode.JP001B.getDescription(),
                    new Throwable(StockServiceConstants.ILLEGAL_TRADE_VALUE)));

    // checking the valid values for quantity and price
    StockService.checkPositive(new BigDecimal(tradeBO.getQuantity()));
    StockService.checkPositive(tradeBO.getPrice());

    // Fetching the Stock record with the symbol from the Gemfire region
    Stock stock = getValidStock(tradeBO.getStockSymbol());

    //Setting the time stamp
    tradeBO.setTimestamp(LocalDateTime.now());

    // Mapping TradeBO to Trade entity
    Trade tradeEntity = tradeMapper.tradeBOToTrade(tradeBO);
    tradeEntity.setStock(stock);

    // Get a Unique sequence from gemfire region for each trade
    int tradeIdentifier = generateUniqueSequence.getUniqueSequence();
    tradeEntity.setTradeIdentifier(tradeIdentifier);

    logger.info("saving trade im trade regign {}", tradeEntity);
    logger.traceEntry("saved trade in gemfire");
    // saving the trade in Trade region
    tradeDao.save(tradeEntity);
  }

  /**
   * Calculate Volume Weighted Stock Price based on trades in past 15 minutes
   *
   * @param symbol the stock symbol to be calculated
   * @return Volume Weighted Stock Price
   */
  @Override
  public BigDecimal getVolumeWeightedStockPrice(String symbol) {

    logger.traceEntry("parameters : symbol {} ", symbol);

    // Throws exception in case stock symbol is null or blank
    Optional.ofNullable(symbol)
        .map(String::trim)
        .filter(string -> !string.isEmpty())
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP003B.getId(),
                    BusinessErrorCode.JP003B.getDescription(),
                    new Throwable(StockServiceConstants.STOCK_SYMBOL_NULL)));

    // Fetching all the trade for a  given Stock Symbol in last 15 minute from trade region in
    // Gemfire
    List<Trade> tradeList =
        tradeDao.getTradeCollectionByTime(symbol, LocalDateTime.now().minusMinutes(15));

    // Throws exception in case No Trade record found for the Stock
    if (tradeList.isEmpty())
      throw new StockMarketServiceException(
          BusinessErrorCode.JP001B.getId(),
          BusinessErrorCode.JP001B.getDescription(),
          new Throwable(StockServiceConstants.TRADE_NOT_FOUND));

    //Mapping Trade List to tradeBO list
    List<TradeBO> tradeBOList = tradeMapper.tradeListToTradeBOList(tradeList);

    BigDecimal priceSum = BigDecimal.ZERO;
    BigInteger quantitySum = BigInteger.ZERO;

    //Iterating over tradeBo list
    for (TradeBO record : tradeBOList) {
      BigDecimal price = record.getPrice();
      StockService.checkPositive(price);
      BigDecimal quantity = new BigDecimal(record.getQuantity());
      StockService.checkPositive(quantity);
      //sum of price *  quantity
      priceSum = priceSum.add(price.multiply(quantity));
      // sum of quantity
      quantitySum = quantitySum.add(record.getQuantity());
    }

    BigDecimal volumeWeightedStockPrice =
        priceSum.divide(new BigDecimal(quantitySum), PRECISION_SCALE, 3);
    logger.traceExit("Result: volumeWeightedStockPrice - {}", volumeWeightedStockPrice);
    return volumeWeightedStockPrice.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Get the valid Stock record from the Gemfire region
   *
   * @param value the value to be checked
   */
  private Stock getValidStock(String stockSymbol) {

    return stockDao
        .findById(stockSymbol)
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP001B.getId(),
                    BusinessErrorCode.JP001B.getDescription(),
                    new Throwable(StockServiceConstants.STOCK_NOT_FOUND + stockSymbol)));
  }

  /**
   * Saves the list of Stock in Gemfire Region
   *
   * @param stockList List of Stock
   */
  @Override
  public void saveStockCollection(List<StockBO> stockBOList) {

    stockDao.saveAll(stockMapper.stockBOListTOStockList(stockBOList));
  }
}
