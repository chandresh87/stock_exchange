/** */
package com.jp.stock.remote;

import com.jp.stock.enums.StockType;
import com.jp.stock.remote.dto.StockDTO;
import com.jp.stock.service.StockService;
import com.jp.stock.service.mapper.StockMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class loads the Stock data from the excel file received from exchange. It runs on the
 * context start up and continue to run every day at 7 o clock periodically to load the EOD files
 * into the system.
 *
 * @author chandresh.mishra
 */
@Component
@PropertySource("com/jp/stock/exchangeData/StockExchange.properties")
public class LoadExchangeData {

  private static final Logger logger = LogManager.getLogger();

  @Autowired private StockService stockQuery;

  @Autowired private StockMapper stockMapper;

  // Exchange file location from the property file.
  @Value("${stock.dataFile}")
  private String dataFile;

  // Runs once on loading of context
  @PostConstruct
  public void onStartup() {

    logger.info("Loading Stock data into System");
    stockQuery.saveStockCollection(
        stockMapper.stockDTOListTOStockBOList(readStockDataFromExcelFile(dataFile)));
  }

  // Runs periodically at specific time of day decided by cron expression
  @Scheduled(cron = "${stock.cron}")
  public void onSchedule() {

    logger.info("Loading Stock data into System periodically");
    stockQuery.saveStockCollection(
        stockMapper.stockDTOListTOStockBOList(readStockDataFromExcelFile(dataFile)));
  }

  /**
   * Read the EOD excel file containing the Stock data It uses Apache POI to read the Excel file
   *
   * @param excelFilePath location of EOD file
   * @return list of stock data
   */
  public List<StockDTO> readStockDataFromExcelFile(String excelFilePath) {

    FileInputStream inputStream = null;
    List<StockDTO> listStock = null;
    try {
      logger.info("Loading Exchange File");
      listStock = new ArrayList<>();

      File myFile = new File(excelFilePath);
      System.out.println("Attempting to read from file in: " + myFile.getCanonicalPath());

      inputStream = new FileInputStream(new File(excelFilePath));

      Workbook workbook = new XSSFWorkbook(inputStream);
      Sheet firstSheet = workbook.getSheetAt(0);
      Iterator<Row> iterator = firstSheet.iterator();
      // Iterating over each row
      while (iterator.hasNext()) {
        Row nextRow = iterator.next();
        Iterator<Cell> cellIterator = nextRow.cellIterator();

        // Leave the first row containing labels
        if (nextRow.getRowNum() == 0) {
          continue;
        }
        StockDTO stock = new StockDTO();
        // Iterating over cell and set the value in stock object
        while (cellIterator.hasNext()) {
          Cell nextCell = cellIterator.next();
          int columnIndex = nextCell.getColumnIndex();

          switch (columnIndex) {
            case 0:
              stock.setStockSymbol((String) getCellValue(nextCell));
              break;

            case 1:
              stock.setStockType(StockType.valueOf(getCellValue(nextCell).toString()));
              break;
            case 2:
              stock.setLastDividend(new BigDecimal((Double) getCellValue(nextCell)));
              break;

            case 3:
              stock.setFixedDividend(new BigDecimal((Double) getCellValue(nextCell)));
              break;

            case 4:
              stock.setParValue(new BigDecimal((Double) getCellValue(nextCell)));
              break;
          }
        }
        listStock.add(stock);
        stock = null;
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        logger.info("Exception while reading the exchange file");
        e.printStackTrace();
      }
    }
    return listStock;
  }

  /**
   * @param cell cell of the each row in excel file
   * @return object value depending on the type of cell value
   */
  private Object getCellValue(Cell cell) {
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        return cell.getStringCellValue();

      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();

      case Cell.CELL_TYPE_NUMERIC:
        return cell.getNumericCellValue();
    }

    return null;
  }
}
