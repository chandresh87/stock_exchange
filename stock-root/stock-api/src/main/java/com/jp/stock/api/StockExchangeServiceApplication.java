package com.jp.stock.api;

import com.jp.stock.api.config.ApiConfig;
import com.jp.stock.api.config.SwaggerConfig;
import com.jp.stock.service.config.ActiveMQConfig;
import com.jp.stock.service.config.GemfireConfig;
import com.jp.stock.service.config.RemoteConfig;
import com.jp.stock.service.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

/**
 * Main Configuration Class.
 *
 * @author chandresh.mishra
 */
@Import(
  value = {
    ApiConfig.class,
    GemfireConfig.class,
    ServiceConfig.class,
    SwaggerConfig.class,
    RemoteConfig.class,
    ActiveMQConfig.class
  }
)
@SpringBootApplication
@EnableJms
public class StockExchangeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockExchangeServiceApplication.class, args);
  }
}
