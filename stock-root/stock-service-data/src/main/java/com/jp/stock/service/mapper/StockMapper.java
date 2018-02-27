/**
 * 
 */
package com.jp.stock.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jp.stock.entity.Stock;
import com.jp.stock.remote.dto.StockDTO;
import com.jp.stock.service.bo.StockBO;

/**
 * @author chandresh.mishra
 *
 */
@Mapper(componentModel="Spring" , unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface StockMapper {

	StockBO stockToStockBO(Stock stock);
	Stock stockBOToStock(StockBO stockBO);
	
	StockBO stockDTOTOStockBO(StockDTO stockDTO);
	
	List<Stock> stockBOListTOStockList(List<StockBO> stockBOList);
	List<StockBO> stockDTOListTOStockBOList(List<StockDTO> stockDTOList);
}
