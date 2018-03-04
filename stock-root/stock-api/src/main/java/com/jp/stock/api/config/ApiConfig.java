package com.jp.stock.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for APIs.
 *
 * @author chandresh.mishra
 */
@Configuration
@ComponentScan(
  value = {"com.jp.stock.api.controller", "com.jp.stock.api.advice", "com.jp.stock.api.mapper"}
)
public class ApiConfig {}
