/** */
package com.jp.stock.service.config;

import com.jp.stock.service.factory.SimpleStockFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * configuration for the services
 *
 * @author chandresh.mishra
 */
@Configuration
@ComponentScan(
  value = {"com.jp.stock.service", "com.jp.stock.service.mapper", "com.jp.stock.remote"}
)
public class ServiceConfig {

  @Bean(name = "SimpleStockFactory")
  public SimpleStockFactory stockFactory() {
    return SimpleStockFactory.getInstance();
  }
}
