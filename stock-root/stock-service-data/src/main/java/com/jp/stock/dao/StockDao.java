/** */
package com.jp.stock.dao;

import com.jp.stock.entity.Stock;
import java.util.Optional;
import org.springframework.data.gemfire.repository.query.annotation.Trace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * It performs the CRUD operation on Stock region by extending Spring data CrudRepository
 *
 * @author chandresh.mishra
 */
@Repository
public interface StockDao extends CrudRepository<Stock, String> {

  /** Saves stock record in gemfire region */
  @Trace
  public Stock save(Stock stock);

  /** Finds the record with the given stock symbol/Key */
  @Trace
  public Optional<Stock> findById(String symbol);
}
