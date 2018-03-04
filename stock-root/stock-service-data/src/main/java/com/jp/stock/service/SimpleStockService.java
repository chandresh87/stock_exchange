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
import java.util.Collection;
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

  @Autowired private TradeMapper tradeMapper;

  @Autowired private StockMapper stockMapper;

  private static final Logger logger = LogManager.getLogger();

  /** The precision scale in the calculation. */
  private static final int PRECISION_SCALE = 7;

  /**
   * Calculates the dividend yield of the given stock based on the given price. The result precision
   * scale is 3 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock to be calculated
   * @param price the price used in calculation
   */
  @Override
  public BigDecimal getDividendYield(String symbol, BigDecimal price) {

    logger.info("Calculating Dividend Yield for the stock symbol: " + symbol);

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
    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Calculates the P/E Ratio of the given stock based on the given price. The result precision
   * scale is 3 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the stock symbol to be calculated
   * @param price the trade price
   */
  @Override
  public BigDecimal getPERatio(String symbol, BigDecimal price) {

    logger.info("Calculating P/E Ratio for the stock symbol: " + symbol);
    // calculating the dividend for the Stock
    BigDecimal dividend = getDividendYield(symbol, price);

    Optional.ofNullable(dividend)
        .filter(decimalValue -> BigDecimal.ZERO.compareTo(decimalValue) < 0)
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP003B.getId(),
                    BusinessErrorCode.JP003B.getDescription(),
                    new Throwable(StockServiceConstants.ILLEGAL_VALUE_OF_DIVIDEND + symbol)));

    BigDecimal peRatio = price.divide(dividend, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    return peRatio.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * Records a trade, with time stamp, quantity of shares, buy or sell indicator and traded price.
   */
  @Override
  public void recordTrade(TradeBO tradeBO) {

    logger.info("saving the trade for the stock symbol:" + tradeBO);
    // Checking for blank and null values
    Optional.ofNullable(tradeBO)
        .filter(validTrade -> StringUtils.isNotBlank(validTrade.getStockSymbol()))
        .orElseThrow(
            () ->
                new StockMarketServiceException(
                    BusinessErrorCode.JP001B.getId(),
                    BusinessErrorCode.JP001B.getDescription(),
                    new Throwable(StockServiceConstants.ILLEGAL_TRADE_VALUE)));

    // checking the valid values
    StockService.checkPositive(new BigDecimal(tradeBO.getQuantity()));
    StockService.checkPositive(tradeBO.getPrice());
    // Fetching the Stock record with the symbol from the Gemfire region
    Stock stock = getValidStock(tradeBO.getStockSymbol());

    tradeBO.setTimestamp(LocalDateTime.now());

    // Mapping TradeBO to Trade
    Trade tradeEntity = tradeMapper.tradeBOToTrade(tradeBO);
    tradeEntity.setStock(stock);

    // Get a Unique sequence from gemfire region for each trade
    int tradeIdentifier = generateUniqueSequence.getUniqueSequence();
    tradeEntity.setTradeIdentifier(tradeIdentifier);

    // saving the trade in Trade region
    tradeDao.save(tradeEntity);
  }

  @Override
  public BigDecimal getVolumeWeightedStockPrice(String symbol) {

    logger.info("Calculating Dividend Yield for the stock symbol: " + symbol);

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

    // Fetching all the trade with given Stock Symbol from trade region in
    // Gemfire
    List<Trade> tradeList =
        tradeDao.getTradeCollectionByTime(symbol, LocalDateTime.now().minusMinutes(15));

    // Throws exception in case No Trade record found for the Stock
    if (tradeList.isEmpty())
      throw new StockMarketServiceException(
          BusinessErrorCode.JP001B.getId(),
          BusinessErrorCode.JP001B.getDescription(),
          new Throwable(StockServiceConstants.TRADE_NOT_FOUND));

    // Get the trade record in last 15 min
    //List<Trade> tradeList = getTradeRecordsByTime(tradeCollection, 15);

    BigDecimal volumeWeightedStockPrice = BigDecimal.ZERO;
    /*if (tradeList.isEmpty()) {
    	return volumeWeightedStockPrice;
    }*/

    //else {

    //Mapping Trade List to trade bo list
    List<TradeBO> tradeBOList = tradeMapper.tradeListToTradeBOList(tradeList);
    tradeList = null;

    BigDecimal priceSum = BigDecimal.ZERO;
    BigInteger quantitySum = BigInteger.ZERO;

    // tradeBOList.parallelStream().reduce( record -> {  BigDecimal price = record.getPrice();   BigDecimal quatity = new BigDecimal(record.getQuantity());

    // })
    for (TradeBO record : tradeBOList) {
      BigDecimal price = record.getPrice();
      StockService.checkPositive(price);
      BigDecimal quatity = new BigDecimal(record.getQuantity());
      StockService.checkPositive(quatity);

      priceSum = priceSum.add(price.multiply(quatity));

      quantitySum = quantitySum.add(record.getQuantity());
    }

    volumeWeightedStockPrice = priceSum.divide(new BigDecimal(quantitySum), PRECISION_SCALE, 3);
    return volumeWeightedStockPrice.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    //}

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
    /*Stock stock = stockDao.findOne(stockSymbol);
    if (stock == null) {
    	throw new StockMarketExchangeException(
    			"No Stock found with this Stock Symbol :" + stockSymbol);

    }

    return stock;*/

  }

  /**
   * Gets the trade records of the given stock in the last given minutes.
   *
   * @param list the list of trade record
   * @param minutes the range of the time to search
   * @return a {@link TradeRecord}s matching the search criterion
   */
  private List<Trade> getTradeRecordsByTime(Collection<Trade> tradeCollection, int minutes) {
    /*List<Trade> result = new ArrayList<Trade>();


    Date currentTime = new Date();
    for (Trade record : tradeCollection) {
    	//if (currentTime.getTime() - record.getTimestamp().getTime() <= minutes * 60 * 1000) {
    		result.add(record);
    	}
    }

    return result;*/
    return null;
  }
  /**
   * Saves the list of Stock in Gemfire Region
   *
   * @param stockList List of Stock
   */
  @Override
  public void saveStockCollection(List<StockBO> stockBOList) {

    stockDao.saveAll(stockMapper.stockBOListTOStockList(stockBOList));
    //stockDao.save(stockMapper.stockBOListTOStockList(stockBOList));
  }
}
