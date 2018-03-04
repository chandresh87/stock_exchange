package com.jp.stock.api.contract;

import com.jp.stock.api.model.response.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = {"Trade Operations"})
public interface TradeAPI {

  /**
   * RESTful API endpoint to trigger geometric mean of prices for all stocks.
   *
   * @return ResponseEntity wrapper containing HTTP status code and calculated geometric mean of
   *     prices.
   */
  @ApiOperation(value = "Calculate the GBCE All Share Index")
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "BAD_REQUEST"),
      @ApiResponse(code = 401, message = "Request was not authorised"),
      @ApiResponse(code = 404, message = "NOT_FOUND"),
      @ApiResponse(code = 500, message = "Syetem is currently experiencing problems.")
    }
  )
  ResponseEntity<Message> getGbse();
}
