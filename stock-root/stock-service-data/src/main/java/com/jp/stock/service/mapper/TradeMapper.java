/** */
package com.jp.stock.service.mapper;

import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/** @author chandresh.mishra */
@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

  Trade tradeBOToTrade(TradeBO tradeBO);

  @Mappings({@Mapping(target = "stockSymbol", source = "stock.symbol")})
  TradeBO tradeToTradeBO(Trade trade);

  List<TradeBO> tradeListToTradeBOList(List<Trade> tradeList);
}
