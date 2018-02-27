/** */
package com.jp.stock.service.factory;

import com.jp.stock.service.StockService;
import com.jp.stock.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory of the services in the Simple Stock application.
 *
 * <p>All external systems or high level layers in the application will access to the services
 * through this factory. It is implemented as a singleton, following the singleton pattern proposed
 * by Bill Pugh.
 *
 * @author chandresh.mishra
 */

public class SimpleStockFactory {

  /** Load the spring context for the Application */
  //SpringService springService = SpringService.getInstance();

  @Autowired private StockService stockService;

  @Autowired private TradeService tradeService;

  /** Private constructor for the factory which prevents new instance */
  private SimpleStockFactory() {
    //springService.getBean(LoadExchangeData.class);
  }

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
   * Gets the instance StockQuery through SimpleStockFactory instance
   *
   * @return An object of the StockQuery service
   */
  public StockService getStockService() {
    return stockService;

    //springService.getBean(StockService.class);
  }

  /**
   * Gets the instance TradeQuery through SimpleStockFactory instance
   *
   * @return An object of the TradeQuery service
   */
  public TradeService getTradeService() {
    return tradeService;

    //springService.getBean(TradeService.class);
  }
}
