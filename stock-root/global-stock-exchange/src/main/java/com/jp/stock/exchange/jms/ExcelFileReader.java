/** */
package com.jp.stock.exchange.jms;

import com.jp.stock.exchange.jms.dto.StockDTO;
import com.jp.stock.exchange.jms.dto.StockType;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * It read the excel file having stock exchange data.
 *
 * @author chandresh.mishra
 */
@Component
public class ExcelFileReader {

  private static final Logger logger = LogManager.getLogger();

  /**
   * Read the EOD excel file containing the Stock data asynchronously .It uses Apache POI to read
   * the Excel file
   *
   * @param excelFilePath location of EOD file
   * @return list of stock data
   */
  // @Async("threadPoolTaskExecutor")
  public List<StockDTO> readStockDataFromExcelFile(String excelFilePath) {

    logger.traceEntry();
    List<StockDTO> listStock = null;
    try (InputStream inputStream =
        this.getClass().getClassLoader().getResourceAsStream(excelFilePath)) {
      logger.info("Loading Exchange File");
      listStock = new ArrayList<>();

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
      logger.error(e);
    }
    logger.debug("Loaded stock data" + listStock.size());
    return listStock;
    //return CompletableFuture.completedFuture(listStock);
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

      default:
        return null;
    }
  }
}
