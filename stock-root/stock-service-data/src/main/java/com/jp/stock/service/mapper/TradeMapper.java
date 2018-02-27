/**
 * 
 */
package com.jp.stock.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;

/**
 * @author chandresh.mishra
 *
 */
@Mapper(componentModel="Spring" , unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface TradeMapper {

	Trade tradeBOToTrade(TradeBO tradeBO);
	TradeBO tradeToTradeBO(Trade trade);
	
	List<TradeBO> tradeListToTradeBOList(List<Trade> tradeList);
	
}
