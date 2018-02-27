/** */
package com.jp.stock.api.controller;

import com.jp.stock.api.contract.TradeAPI;
import com.jp.stock.api.model.response.Message;
import com.jp.stock.service.factory.SimpleStockFactory;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the trade operations
 *
 * @author chandresh.mishra
 */
@RestController
@RequestMapping("/trade")
public class TradeController implements TradeAPI {

  private SimpleStockFactory simpleStockFactory = SimpleStockFactory.getInstance();

  /* (non-Javadoc)
   * @see com.jp.stock.api.controller.TradeAPI#getGbse()
   */
  @Override
  @GetMapping(path = "/gbse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> getGbse() {
    BigDecimal gbse = simpleStockFactory.getTradeService().calculateGBCEAllShareIndex();
    Message message = new Message();
    message.setResult(gbse);
    message.setResponseMessgae("GBCE All Share Index");

    return new ResponseEntity<>(message, HttpStatus.OK);
  }
}
