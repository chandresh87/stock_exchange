/**
 * 
 */
package com.jp.stock.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.service.bo.TradeBO;

/**
 * Mapper to map model  and BO.
 * @author chandresh.mishra
 *
 */
@Mapper(componentModel="Spring" , unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ModelMapper {

	TradeBO tradeModelToTradeBO(TradeModel model);
	TradeModel tradeBOToTradeModel(TradeBO tradeBO);
	
}
