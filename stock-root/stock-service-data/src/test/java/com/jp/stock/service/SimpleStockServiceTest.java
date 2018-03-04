/** */
package com.jp.stock.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jp.stock.dao.StockDao;
import com.jp.stock.dao.TradeDao;
import com.jp.stock.entity.Stock;
import com.jp.stock.entity.Trade;
import com.jp.stock.service.bo.TradeBO;
import com.jp.stock.service.data.TestServiceData;
import com.jp.stock.service.exception.StockMarketServiceException;
import com.jp.stock.service.mapper.StockMapper;
import com.jp.stock.service.mapper.TradeMapper;
import com.jp.stock.service.util.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the SimpleStockService
 *
 * @author chandresh.mishra
 */
@RunWith(SpringRunner.class)
public class SimpleStockServiceTest {

  @Mock private StockDao stockDao;

  @Mock private TradeDao tradeDao;

  @Mock private SequenceGenerator generateUniqueSequence;

  @Mock private TradeMapper tradeMapper;

  @Mock private StockMapper stockMapper;

  @InjectMocks private StockService stockService = new SimpleStockService();

  @Test
  public void getCommonDividendYieldTest() {
    //Mocking stock dao
    when(this.stockDao.findById(any(String.class))).thenReturn(TestServiceData.getCommonStock());

    //Mocking stock mapper
    when(this.stockMapper.stockToStockBO(any(Stock.class)))
        .thenReturn(TestServiceData.getCommonStockBO().build());

    BigDecimal dividend = stockService.getDividendYield("TEA", new BigDecimal(100));
    assertEquals(0l, dividend.longValue());
  }

  @Test
  public void getPreferredDividendYieldTest() {
    //Mocking stock dao
    when(this.stockDao.findById(any(String.class))).thenReturn(TestServiceData.getPreferredStock());

    //Mocking stock mapper
    when(this.stockMapper.stockToStockBO(any(Stock.class)))
        .thenReturn(TestServiceData.getPreferredStockBO().build());

    BigDecimal dividend = stockService.getDividendYield("GIN", new BigDecimal(100));
    assertTrue(dividend.equals(new BigDecimal(0.020).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
  }

  @Test(expected = StockMarketServiceException.class)
  public void getDividendYieldExceptionTest() {
    //Mocking
    when(this.stockDao.findById(any(String.class))).thenReturn(TestServiceData.getCommonStock());
    //Mocking stock mapper
    when(this.stockMapper.stockToStockBO(any(Stock.class)))
        .thenReturn(TestServiceData.getCommonStockBO().build());

    BigDecimal dividend = stockService.getDividendYield("TEA", new BigDecimal(-100));
    assertEquals(0l, dividend.longValue());
  }

  @Test
  public void getPERatioTest() {
    //Mocking stock dao
    when(this.stockDao.findById(any(String.class))).thenReturn(TestServiceData.getPreferredStock());
    //Mocking stock mapper
    when(this.stockMapper.stockToStockBO(any(Stock.class)))
        .thenReturn(TestServiceData.getPreferredStockBO().build());

    BigDecimal peRatio = stockService.getPERatio("GIN", new BigDecimal(100));
    assertTrue(peRatio.equals(new BigDecimal(5000).setScale(3, BigDecimal.ROUND_HALF_EVEN)));
  }

  @Test
  public void saveTradeTest() {
    //Mocking stock dao
    when(this.stockDao.findById(any(String.class))).thenReturn(TestServiceData.getCommonStock());
    //Mocking trade mapper
    when(this.tradeMapper.tradeBOToTrade(any(TradeBO.class)))
        .thenReturn(TestServiceData.getTrade());

    //Mocking unique sequence dao
    when(this.generateUniqueSequence.getUniqueSequence()).thenReturn(1);
    //Mocking trade dao
    when(this.tradeDao.save(any(Trade.class))).thenReturn(TestServiceData.getTrade());

    stockService.recordTrade(TestServiceData.getTradeBO().build());
  }

  @Test(expected = StockMarketServiceException.class)
  public void saveTradeExceptionTest() {

    stockService.recordTrade(null);
  }

  @Test(expected = StockMarketServiceException.class)
  public void getVolumeWeightedStockPriceExceptionTest() {
    stockService.getVolumeWeightedStockPrice(" ");
  }

  @Test
  public void getVolumeWeightedStockPriceTest() {
    List<Trade> tradeList = new ArrayList<>();
    List<TradeBO> tradeBOList = new ArrayList<>();

    tradeList.add(TestServiceData.getTrade());
    tradeList.add(TestServiceData.getTrade());

    tradeBOList.add(TestServiceData.getTradeBO().build());
    tradeBOList.add(TestServiceData.getTradeBO().price(new BigDecimal(200)).build());

    //Mocking trade dao
    when(this.tradeDao.getTradeCollectionByTime(any(String.class), any(LocalDateTime.class)))
        .thenReturn(tradeList);
    //Mocking trade mapper
    when(this.tradeMapper.tradeListToTradeBOList(any(List.class))).thenReturn(tradeBOList);

    BigDecimal volumeWeightedStockPrice = stockService.getVolumeWeightedStockPrice("TEA");
    assertTrue(
        volumeWeightedStockPrice.equals(
            new BigDecimal(150).setScale(0, BigDecimal.ROUND_HALF_EVEN)));
  }
}
