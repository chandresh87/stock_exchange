package com.jp.stock.exchange.jms;

import com.jp.stock.exchange.jms.dto.CustomList;
import com.jp.stock.exchange.jms.dto.StockDTO;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
/**
 * This class is responsible for sending the data to the JMS stock queue.
 *
 * @author chandresh.mishra
 */
@Component
public class QueueSender {

  private static final String STOCK_QUEUE = "stock-queue";
  @Autowired private JmsTemplate jmsTemplate;

  private static final Logger logger = LogManager.getLogger();

  public void send(List<StockDTO> stockList) {
    logger.info("sending data to the stock queue " + stockList);
    //StockDTO dto = stockList.get(0);
    //jmsTemplate.convertAndSend(STOCK_QUEUE, dto);
    //stockList.forEach(stock -> jmsTemplate.convertAndSend(STOCK_QUEUE, stock));
    jmsTemplate.convertAndSend(STOCK_QUEUE, new CustomList(stockList));
  }
}
