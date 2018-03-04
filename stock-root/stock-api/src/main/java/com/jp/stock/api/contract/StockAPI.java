package com.jp.stock.api.contract;

import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.api.model.response.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;

/**
 * Stock REST Controller Interface.
 *
 * @author chandresh.mishra
 */
@Api(tags = {"Stock Operations"})
public interface StockAPI {

  /**
   * RESTful API endpoint to trigger Dividend calculation.
   *
   * @param stockSymbol The unique symbol for stock
   * @param marketPrice Market price
   * @return ResponseEntity wrapper containing HTTP status code and calculated dividend
   */
  @ApiOperation(value = "calculate the dividend yield")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "BAD_REQUEST"),
      @ApiResponse(code = 401, message = "Request was not authorised"),
      @ApiResponse(code = 404, message = "NOT_FOUND"),
      @ApiResponse(code = 500, message = "Syetem is currently experiencing problems.")
    }
  )
  ResponseEntity<Message> getDividend(String stockSymbol, BigDecimal marketPrice);

  /**
   * RESTful API endpoint to trigger PE Ration calculation.
   *
   * @param stockSymbol The unique symbol for stock
   * @param marketPrice Market price
   * @return ResponseEntity wrapper containing HTTP status code and calculated PE Ration
   */
  @ApiOperation(value = "calculate the P/E Ratio")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "BAD_REQUEST"),
      @ApiResponse(code = 401, message = "Request was not authorised"),
      @ApiResponse(code = 404, message = "NOT_FOUND"),
      @ApiResponse(code = 500, message = "Syetem is currently experiencing problems.")
    }
  )
  ResponseEntity<Message> getPERation(String stockSymbol, BigDecimal marketPrice);

  /**
   * RESTful API endpoint to save trade.
   *
   * @param tradeModel Trade record
   * @return ResponseEntity wrapper containing HTTP status code and success message
   */
  @ApiOperation(value = "Record a trade")
  @ApiResponses(
    value = {
      @ApiResponse(code = 201, message = "saved succesfully"),
      @ApiResponse(code = 400, message = "BAD_REQUEST"),
      @ApiResponse(code = 401, message = "Request was not authorised"),
      @ApiResponse(code = 500, message = "Syetem is currently experiencing problems.")
    }
  )
  ResponseEntity<String> saveTrade(TradeModel tradeModel);

  /**
   * RESTful API endpoint to trigger volume weighted stock price calculation.
   *
   * @param stockSymbol The unique symbol for stock
   * @return ResponseEntity wrapper containing HTTP status code and calculated Volume Weighted Stock
   *     Price
   */
  @ApiOperation(value = "Calculate Volume Weighted Stock Price")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "BAD_REQUEST"),
      @ApiResponse(code = 401, message = "Request was not authorised"),
      @ApiResponse(code = 404, message = "NOT_FOUND"),
      @ApiResponse(code = 500, message = "Syetem is currently experiencing problems.")
    }
  )
  ResponseEntity<Message> getVolumeWeightedStockPrice(String stockSymbol);
}
