/** */
package com.jp.stock.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jp.stock.dao.TradeDao;
import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.data.TestServiceData;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.mapper.TradeMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

/** @author chandresh.mishra */
@RunWith(SpringRunner.class)
public class SimpleTradeServiceTest {

  @Mock TradeDao tradeDao;

  @Mock TradeMapper tradeMapper;

  @InjectMocks TradeService tradeService = new SimpleTradeService();

  @Test
  public void calculateGBCEAllShareIndexTest() {
    List<Trade> tradeList = new ArrayList<>();
    List<TradeBO> tradeBOList = new ArrayList<>();

    tradeList.add(TestServiceData.getTrade());
    tradeList.add(TestServiceData.getTrade());

    tradeBOList.add(TestServiceData.getTradeBO().build());
    tradeBOList.add(TestServiceData.getTradeBO().price(new BigDecimal(200)).build());

    when(this.tradeMapper.tradeListToTradeBOList(any(List.class))).thenReturn(tradeBOList);
    when(this.tradeDao.findAll()).thenReturn(tradeList);

    BigDecimal geometricMean = tradeService.calculateGBCEAllShareIndex();
    assertTrue(geometricMean.equals(new BigDecimal(141).setScale(0, BigDecimal.ROUND_HALF_EVEN)));
  }

  @Test(expected = StockMarketServiceException.class)
  public void calculateGBCEAllShareIndexExceptionTest() {
    when(this.tradeDao.findAll()).thenReturn(new ArrayList<>());
    tradeService.calculateGBCEAllShareIndex();
  }
}
