/**
 * 
 */
package com.jp.stock.service.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;

import com.gemstone.gemfire.cache.GemFireCache;
import com.jp.stock.entity.Stock;

/**
 * Configuration for Gemfire
 * 
 * @author chandresh.mishra
 *
 */
@Configuration
@ComponentScan(
		  value = {
		    "com.jp.stock.dao"
		  }
		)
public class GemfireConfiguration {

	
	 @Bean
	    Properties gemfireProperties() {
	        Properties gemfireProperties = new Properties();
	        gemfireProperties.setProperty("name","SpringDataGemFireApplication");
	        gemfireProperties.setProperty("mcast-port", "0");
	        gemfireProperties.setProperty("log-level", "config");
	        return gemfireProperties;
	    }
	 
	    @Bean
	    CacheFactoryBean gemfireCache() {
	        CacheFactoryBean gemfireCache = new CacheFactoryBean();
	        gemfireCache.setClose(true);
	        gemfireCache.setProperties(gemfireProperties());
	        return gemfireCache;
	    }
	 
	    @Bean(name="Stock")
	    LocalRegionFactoryBean<String, Stock> getStock(final GemFireCache cache) {
	        LocalRegionFactoryBean<String, Stock> stockRegion = new LocalRegionFactoryBean();
	     
	        stockRegion.setCache(cache);
	        stockRegion.setName("Stock");

	        return stockRegion;
	    }
	    

	    @Bean(name="Trade")
	    LocalRegionFactoryBean<Integer, Stock> getTrade(final GemFireCache cache) {
	        LocalRegionFactoryBean<Integer, Stock> tradeRegion = new LocalRegionFactoryBean();
	     
	        tradeRegion.setCache(cache);
	        tradeRegion.setName("Trade");
	        return tradeRegion;
	    }
}
