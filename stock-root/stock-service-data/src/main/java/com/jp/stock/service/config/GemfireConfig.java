/** */
package com.jp.stock.service.config;

import com.jp.stock.entity.Stock;
import com.jp.stock.entity.Trade;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Configuration for the Gemfire
 *
 * @author chandresh.mishra
 */
@Configuration
@EnableGemfireRepositories(basePackages = "com.jp.stock.dao")
@ClientCacheApplication(name = "StockDataGemFire", logLevel = "OFF")
@EnableEntityDefinedRegions(
  basePackageClasses = {Stock.class, Trade.class},
  clientRegionShortcut = ClientRegionShortcut.LOCAL
)
public class GemfireConfig {}
