package com.jp.stock.api.contract;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.api.model.response.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = {"Stock Operations"})
public interface StockAPI {

	/**
	   * RESTful API endpoint to trigger snapshot calculation.
	   *
	   * @param nino the nino
	   */
	  @ApiOperation(value = "Trigger snapshot calculation")
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

	ResponseEntity<Message> getPERation(String stockSymbol, BigDecimal marketPrice);

	ResponseEntity<String> saveTrade(TradeModel tradeModel);

	ResponseEntity<Message> getPERation(String stockSymbol);

}