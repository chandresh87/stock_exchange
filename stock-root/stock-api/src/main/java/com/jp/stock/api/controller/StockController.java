/**
 * 
 */
package com.jp.stock.api.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jp.stock.api.contract.StockAPI;
import com.jp.stock.api.mapper.ModelMapper;
import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.api.model.response.Message;
import com.jp.stock.service.factory.SimpleStockFactory;

/**
 * @author chandresh.mishra
 *
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockAPI {

	private SimpleStockFactory simpleStockFactory=SimpleStockFactory.getInstance();
	
	@Autowired
	private ModelMapper modelMapper;
	
	/* (non-Javadoc)
	 * @see com.jp.stock.api.controller.StockAPI#getDividend(java.lang.String, java.math.BigDecimal)
	 */
	@Override
	@GetMapping(path = "/dividend", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDividend(@RequestParam(value = "stock", required = true) String stockSymbol,@RequestParam(value = "price", required = true) BigDecimal marketPrice)
	{
		BigDecimal dividend=simpleStockFactory.getStockService().getDividendYield(stockSymbol, marketPrice);
		Message message=new Message();
		message.setResult(dividend);
		message.setResponseMessgae("Dividend for the Stock" + stockSymbol);
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.jp.stock.api.controller.StockAPI#getPERation(java.lang.String, java.math.BigDecimal)
	 */
	@Override
	@GetMapping(path = "/pe", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getPERation(@RequestParam(value = "stock", required = true) String stockSymbol,@RequestParam(value = "price", required = true) BigDecimal marketPrice)
	{
		BigDecimal ratio=simpleStockFactory.getStockService().getPERatio(stockSymbol, marketPrice);
		Message message=new Message();
		message.setResult(ratio);
		message.setResponseMessgae("PE Ratio for the Stock" + stockSymbol);
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.jp.stock.api.controller.StockAPI#saveTrade(com.jp.stock.api.model.request.TradeModel)
	 */
	@Override
	@PostMapping(path = "/saveTrade", produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveTrade(@Valid @RequestBody TradeModel tradeModel)
	{
		simpleStockFactory.getStockService().recordTrade(modelMapper.tradeModelToTradeBO(tradeModel));;
		
		return new ResponseEntity<>("saved succesfully",HttpStatus.CREATED);
	}
	
	/* (non-Javadoc)
	 * @see com.jp.stock.api.controller.StockAPI#getPERation(java.lang.String)
	 */
	@Override
	@GetMapping(path = "/volumeWeightedPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getPERation(@RequestParam(value = "stock", required = true) String stockSymbol)
	{
		BigDecimal volume=simpleStockFactory.getStockService().getVolumeWeightedStockPrice(stockSymbol);
		Message message=new Message();
		message.setResult(volume);
		message.setResponseMessgae("Volume Weighted Stock Price for the Stock" + stockSymbol);
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
}
