package com.jp.stock.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.jp.stock.api.config.ApiConfig;
import com.jp.stock.service.config.GemfireConfiguration;

import io.swagger.annotations.Api;

@Import(
		  value = {
				  ApiConfig.class,
				  GemfireConfiguration.class
		  })
@SpringBootApplication
public class StockExchangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockExchangeServiceApplication.class, args);
	}
}
