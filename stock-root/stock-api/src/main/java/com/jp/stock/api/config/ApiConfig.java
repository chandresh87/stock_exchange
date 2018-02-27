package com.jp.stock.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class for APIs.
 *
 * @author chandresh.mishra
 */
@Configuration
@ComponentScan(
  value = {"com.jp.stock.api.controller", "com.jp.stock.api.advice", "com.jp.stock.api.mapper"}
)
@Import(value = {SwaggerConfig.class})
public class ApiConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
