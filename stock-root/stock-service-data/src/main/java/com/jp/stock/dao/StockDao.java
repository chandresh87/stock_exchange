/**
 * 
 */
package com.jp.stock.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jp.stock.entity.Stock;

/**
 * It performs the CRUD operation on Stock region by extending Spring data
 * CrudRepository
 * 
 * @author chandresh.mishra
 * 
 */
@Repository
public interface StockDao extends CrudRepository<Stock, String> {

	/**
	 * Saves stock record in gemfire region
	 */
	public Stock save(Stock stock);

	/**
	 * Finds the record with the given stock symbol/Key
	 */
	public Stock findOne(String symbol);

}
