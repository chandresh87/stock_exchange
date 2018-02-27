package com.jp.stock.api.contract;

import com.jp.stock.api.model.response.Message;
import org.springframework.http.ResponseEntity;

public interface TradeAPI {

  ResponseEntity<Message> getGbse();
}
