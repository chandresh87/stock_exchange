/** */
package com.jp.stock.exchange.jms;

import com.jp.stock.exchange.jms.dto.StockDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class loads the Stock data from the excel file and call the JMS queue sender to send the
 * data to the stock queue. It runs on the context start up and continue to run every day at 7 o
 * clock periodically to load the EOD files into the system.
 *
 * @author chandresh.mishra
 */
@Component
@PropertySource("classpath:/com/jp/stock/exchangeData/StockExchange.properties")
public class LoadExchangeData {

  private static final Logger logger = LogManager.getLogger();
  private static final String UNABLE_TO_READ_FILE = "unable to load the exchange data";

  @Autowired private ExcelFileReader fileReader;
  @Autowired private QueueSender stockQueueSender;

  private CompletableFuture<List<StockDTO>> futureStockList;

  // Exchange file location from the property file.
  @Value("${stock.dataFile}")
  private String dataFile;

  // Runs once on loading of context
  @PostConstruct
  public void onStartup() {

    logger.info("Loading Stock data into System");
    //futureStockList = fileReader.readStockDataFromExcelFile(dataFile);

    /*if (futureStockList.isDone()) {
    try {*/
    stockQueueSender.send(fileReader.readStockDataFromExcelFile(dataFile));
    //      } catch (InterruptedException | ExecutionException e) {
    //        logger.error(e);
    //        throw new ExchangeException(UNABLE_TO_READ_FILE, new Throwable(UNABLE_TO_READ_FILE));
    //      }
    //}
  }

  // Runs periodically at specific time of day decided by cron expression
  @Scheduled(cron = "${stock.cron}")
  public void onSchedule() {

    logger.info("Loading Stock data into System periodically");
    //futureStockList = fileReader.readStockDataFromExcelFile(dataFile);
    stockQueueSender.send(fileReader.readStockDataFromExcelFile(dataFile));
    //    if (futureStockList.isDone()) {
    //      try {
    //        stockQueueSender.send(futureStockList.get());
    //      } catch (InterruptedException | ExecutionException e) {
    //        logger.error(e);
    //        throw new ExchangeException(UNABLE_TO_READ_FILE, new Throwable(UNABLE_TO_READ_FILE));
    //      }
    //    }
  }
}
