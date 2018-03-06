/** */
package com.jp.stock.service.config;

import com.jp.stock.exchange.config.ExchangeServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for the remote global exchange service
 *
 * @author chandresh.mishra
 */
@Configuration
@Import({ExchangeServiceConfig.class})
public class RemoteConfig {}
