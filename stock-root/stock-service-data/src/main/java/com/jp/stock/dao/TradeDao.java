/** */
package com.jp.stock.dao;

import com.jp.stock.entity.Trade;
import java.util.List;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * It performs the CRUD operation on Trade region by extending Spring data CrudRepository
 *
 * @author chandresh.mishra
 */
@Repository
public interface TradeDao extends CrudRepository<Trade, Integer> {

  /** Saves Trade record in gemfire region */
  public Trade save(Trade trade);

  /**
   * Get the all the trade record for a given stock symbol in last 15 min from gemfire Trade region
   */
  @Query("SELECT * FROM /Trade T WHERE T.stock.symbol =$1 ORDER BY T.timestamp desc LIMIT 15")
  public List<Trade> findAllTradeForStock(String stock);

  /** Get all the trade from the Trade region */
  public List<Trade> findAll();
}
