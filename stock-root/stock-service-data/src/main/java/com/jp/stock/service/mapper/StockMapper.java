/** */
package com.jp.stock.service.mapper;

import com.jp.stock.entity.Stock;
import com.jp.stock.exchange.jms.dto.StockDTO;
import com.jp.stock.service.bo.StockBO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

/**
 * It implements the mapping between stock entity and stock business object.
 *
 * @author chandresh.mishra
 */
@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMapper {

  @Mappings({@Mapping(target = "stockSymbol", source = "symbol")})
  StockBO stockToStockBO(Stock stock);

  @Mappings({@Mapping(target = "symbol", source = "stockSymbol")})
  Stock stockBOToStock(StockBO stockBO);

  @ValueMappings({
    @ValueMapping(target = "COMMON", source = "COMMON"),
    @ValueMapping(target = "PREFERRED", source = "PREFERRED")
  })
  com.jp.stock.enums.StockType map(com.jp.stock.exchange.jms.dto.StockType value);

  StockBO stockDTOTOStockBO(StockDTO stockDTO);

  List<Stock> stockBOListTOStockList(List<StockBO> stockBOList);

  List<StockBO> stockDTOListTOStockBOList(List<StockDTO> stockDTOList);
}
