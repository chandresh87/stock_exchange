package com.jp.stock;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jp.stock.api.model.request.TradeIndicator;
import com.jp.stock.api.model.request.TradeModel;
import com.jp.stock.dao.TradeDao;
import com.jp.stock.entity.Trade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**
 * Integration Test for all the operations available in simple stock Rest API
 *
 * @author chandresh.mishra
 */
public class StockIntegrationTest extends StockBaseIntegrationTest {

  private @Autowired TradeDao tradeDao;

  @Test
  public void getDividendTest() throws Exception {

    this.mockMvc
        .perform(get("/stock/dividend").param("stock", "GIN").param("price", "10"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.result").value(0.002))
        .andExpect(jsonPath("$.responseMessgae").value("Dividend for the Stock :GIN"));
  }

  @Test
  public void getPERatioTest() throws Exception {

    this.mockMvc
        .perform(get("/stock/pe").param("stock", "GIN").param("price", "10"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.result").value(5000))
        .andExpect(jsonPath("$.responseMessgae").value("PE Ratio for the Stock :GIN"));
  }

  @Test
  public void saveTradeTest() throws Exception {

    postTrade();

    List<Trade> tradeList = tradeDao.findAll();
    assertEquals(1, tradeList.size());
    assertEquals("TEA", tradeList.get(0).getStock().getSymbol());
  }

  @Test
  public void getVolumeWeightedStockPriceTest() throws JsonProcessingException, Exception {

    //Saving two Trade record before calling the Volume Weighted Stock Price calculation
    postTrade();

    postTrade();

    this.mockMvc
        .perform(get("/stock/volumeWeightedPrice").param("stock", "TEA"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.result").value(100))
        .andExpect(
            jsonPath("$.responseMessgae").value("Volume Weighted Stock Price for the Stock :TEA"));
  }

  @Test
  public void getGbse() throws JsonProcessingException, Exception {
    //Saving two Trade record
    postTrade();
    postTrade();

    this.mockMvc
        .perform(get("/trade/gbse"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.result").value(100))
        .andExpect(jsonPath("$.responseMessgae").value("GBCE All Share Index"));
  }

  private void postTrade() throws JsonProcessingException, Exception {
    this.mockMvc
        .perform(
            post("/stock/saveTrade")
                .contentType(APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(getTradeModel())))
        .andExpect(status().isCreated());
  }

  private TradeModel getTradeModel() {

    TradeModel tradeModel = new TradeModel();
    tradeModel.setIndicator(TradeIndicator.BUY);
    tradeModel.setPrice(new BigDecimal(100));
    tradeModel.setStockSymbol("TEA");
    tradeModel.setQuantity(new BigInteger("150"));
    return tradeModel;
  }
}
