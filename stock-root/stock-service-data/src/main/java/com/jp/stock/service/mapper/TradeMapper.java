/** */
package com.jp.stock.service.mapper;

import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * It implements the mapping between trade entity and trade business object.
 *
 * @author chandresh.mishra
 */
@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

  @Mappings({@Mapping(target = "tradeTimestamp", source = "timestamp")})
  Trade tradeBOToTrade(TradeBO tradeBO);

  @Mappings({
    @Mapping(target = "stockSymbol", source = "stock.symbol"),
    @Mapping(target = "timestamp", source = "tradeTimestamp")
  })
  TradeBO tradeToTradeBO(Trade trade);

  List<TradeBO> tradeListToTradeBOList(List<Trade> tradeList);
}
