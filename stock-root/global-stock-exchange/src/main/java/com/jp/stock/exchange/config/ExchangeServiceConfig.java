package com.jp.stock.exchange.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * configuration for the exchange services
 *
 * @author chandresh.mishra
 */
@Configuration
@ComponentScan(value = {"com.jp.stock.exchange.jms"})
public class ExchangeServiceConfig {}
