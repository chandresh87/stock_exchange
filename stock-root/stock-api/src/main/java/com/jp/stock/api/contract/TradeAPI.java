package com.jp.stock.api.contract;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.jp.stock.api.model.response.Message;

public interface TradeAPI {

	ResponseEntity<Message> getGbse();

}