/** */
package com.jp.stock.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.jp.stock.service.factory.SimpleStockFactory;

/**
 * configuration for service
 *
 * @author chandresh.mishra
 */
@Configuration
@ComponentScan(
  value = {"com.jp.stock.service", "com.jp.stock.service.mapper", "com.jp.stock.remote"}
)
public class ServiceConfig {

  // bean used to for getting the values from the property file
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
  
  
  @Bean(name = "SimpleStockFactory")
  public SimpleStockFactory stockFactory() {
	  return SimpleStockFactory.getInstance();
  }
}
