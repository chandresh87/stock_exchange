/** */
package com.jp.stock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.jp.stock.api.model.request.TradeIndicator;
import com.jp.stock.api.model.request.TradeModel;


/** @author chandresh.mishra */
public class StockIntegrationTest extends StockBaseIntegrationTest {
  private static final String API_STOCK = "/stock";

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
  public void getPERatioTest()  throws Exception {
    
        this.mockMvc
            .perform(get("/stock/pe").param("stock", "GIN").param("price", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.result").value(5000))
            .andExpect(jsonPath("$.responseMessgae").value("PE Ratio for the Stock :GIN"));
  }
  
  @Test
  public void saveTradeTest() throws Exception {
    
	 
        this.mockMvc
            .perform(post("/stock/saveTrade").contentType(APPLICATION_JSON_UTF8).content(this.objectMapper.writeValueAsString( getTradeModel())))
			.andExpect(status().isCreated());
        
          
  }
  

  private   TradeModel getTradeModel() {
	  
	  TradeModel tradeModel=new TradeModel();
	  tradeModel.setIndicator(TradeIndicator.BUY);
	  tradeModel.setPrice(new BigDecimal(100));
	  tradeModel.setStockSymbol("TEA");
	  tradeModel.setQuantity(new BigInteger("150"));
    return tradeModel;
  }
}
