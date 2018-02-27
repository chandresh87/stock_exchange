/** */
package com.jp.stock.api.mapper;

import com.jp.stock.api.model.request.TradeIndicator;
import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.service.bo.TradeBO;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper to map model and BO.
 *
 * @author chandresh.mishra
 */
@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestModelMapper {

  @Mappings({@Mapping(target = "indicator", source = "indicator")})
  TradeBO tradeModelToTradeBO(TradeModel model);

  TradeModel tradeBOToTradeModel(TradeBO tradeBO);

  @ValueMappings({
    @ValueMapping(target = "BUY", source = "BUY"),
    @ValueMapping(target = "SELL", source = "SELL")
  })
  com.jp.stock.api.model.request.TradeIndicator tradeIndicatorToTradeIndicator(
      com.jp.stock.enums.TradeIndicator tradeIndicator);

  @ValueMappings({
    @ValueMapping(target = "BUY", source = "BUY"),
    @ValueMapping(target = "SELL", source = "SELL")
  })
  com.jp.stock.enums.TradeIndicator map(com.jp.stock.api.model.request.TradeIndicator value);
}
