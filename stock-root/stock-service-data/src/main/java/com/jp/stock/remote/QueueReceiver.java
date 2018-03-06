package com.jp.stock.remote;

import com.jp.stock.exchange.jms.dto.StockDTO;
import com.jp.stock.service.StockService;
import com.jp.stock.service.mapper.StockMapper;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for receiving the data from JMS stock queue and save to gemfire.
 *
 * @author chandresh.mishra
 */
@Component
public class QueueReceiver {

  private static final Logger logger = LogManager.getLogger();
  private static final String STOCK_QUEUE = "stock-queue";

  @Autowired private StockService stockService;

  @Autowired private StockMapper stockMapper;

  @JmsListener(destination = STOCK_QUEUE, containerFactory = "jmsListenerContainerFactory")
  public void receive(@Payload List<StockDTO> stockList) {
    logger.info("receiving data from stock queue" + stockList);

    /* for(int i=0;i<stockList.size();i++)
    {
    	LinkedHashMap stockMap=(LinkedHashMap) stockList.get(i);

    	stockMap.forEach((k,v) -> logger.info("receiving stock key " + k + " receiving stock value "+v));

    }*/

    stockService.saveStockCollection(stockMapper.stockDTOListTOStockBOList(stockList));
  }
}
