package com.jp.stock.service.factory;

import com.jp.stock.service.StockService;
import com.jp.stock.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Factory of the services in the Simple Stock application.
 *
 * <p>All external systems or high level layers in the application will access to the services
 * through this factory. It is implemented as a singleton, following the singleton pattern proposed
 * by Bill Pugh.
 *
 * <p>In this implementation it uses autowiring of the services and return the autowired instance
 * but can be changed any time to return any implementation of services with out effecting the other
 * layers.
 *
 * @author chandresh.mishra
 */
public class SimpleStockFactory {

  @Autowired private StockService stockService;

  @Autowired private TradeService tradeService;

  /** Private constructor for the factory which prevents new instance */
  private SimpleStockFactory() {}

  /**
   * Holder class for the singleton factory instance. It is loaded on the first execution of
   * SimpleStockFactory#getInstance()}
   */
  private static class SingletonHelper {
    private static final SimpleStockFactory INSTANCE = new SimpleStockFactory();
  }

  /**
   * Gets the singleton instance of the factory of the services in the Simple Stock application.
   *
   * @return An object of the SimpleStockFactory, which represents the factory to access to all
   *     services in the Simple Stock application.
   */
  public static SimpleStockFactory getInstance() {
    return SingletonHelper.INSTANCE;
  }

  /**
   * Gets the instance StockService through SimpleStockFactory instance
   *
   * @return An object of the StockService
   */
  public StockService getStockService() {
    return stockService;
  }

  /**
   * Gets the instance of tradeService through SimpleStockFactory instance
   *
   * @return An object of the tradeService
   */
  public TradeService getTradeService() {
    return tradeService;
  }
}
