package com.jp.stock.dao;

import com.jp.stock.entity.Trade;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.gemfire.repository.query.annotation.Trace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * It performs the CRUD operation on Trade region by extending Spring data CrudRepository
 *
 * @author chandresh.mishra
 */
@Repository
public interface TradeDao extends CrudRepository<Trade, Integer> {

  /** Saves Trade record in gemfire region */
  @SuppressWarnings("unchecked")
  public Trade save(Trade trade);

  /**
   * Get the all the trade record for a given stock symbol in last 15 minute from gemfire Trade
   * region
   */
  @Trace
  public List<Trade> findByStockSymbolAndTradeTimestampGreaterThanEqual(
      @Param("symbol") String stock, @Param("tradeTimestamp") LocalDateTime dateTime);

  /** Get all the trade from the Trade region */
  @Trace
  public List<Trade> findAll();

  /** Default method wrapping findByStockSymbolAndTradeTimestampGreaterThanEqual for convenience */
  default List<Trade> getTradeCollectionByTime(String stock, LocalDateTime dateTime) {
    return findByStockSymbolAndTradeTimestampGreaterThanEqual(stock, dateTime);
  }
}
