package com.jp.stock.api;

import com.jp.stock.api.config.ApiConfig;
import com.jp.stock.api.config.SwaggerConfig;
import com.jp.stock.service.config.GemfireConfiguration;
import com.jp.stock.service.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main Configuration Class.
 *
 * @author chandresh.mishra
 */
@Import(
  value = {ApiConfig.class, GemfireConfiguration.class, ServiceConfig.class, SwaggerConfig.class}
)
@SpringBootApplication
public class StockExchangeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockExchangeServiceApplication.class, args);
  }
}
