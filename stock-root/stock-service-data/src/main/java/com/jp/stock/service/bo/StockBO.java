/**
 * 
 */
package com.jp.stock.service.bo;

import java.math.BigDecimal;

import com.jp.stock.enums.StockType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Stock business class to apply business logic on stock.
 *
 * @author chandresh.mishra
 *
 */
@ToString
public class StockBO  {

	/** The stock symbol */
	@Getter
	@Setter
	private String stockSymbol;

	/** The last dividend value */
	@Getter
	@Setter
	private BigDecimal lastDividend;

	/** The par value. */
	@Getter
	@Setter
	private BigDecimal parValue;

	/** The price of this stock. */
	@Getter
	@Setter
	private BigDecimal price;

	/** The Type of this stock. */
	@Getter
	@Setter
	private StockType stockType;

	/** The last dividend value */
	@Getter
	@Setter
	private BigDecimal fixedDividend;
	
	public StockBO(StockBOBuilder builder)
	{
		this.fixedDividend=builder.fixedDividend;
		this.stockSymbol=builder.stockSymbol;
		this.parValue=builder.parValue;
		this.price=builder.price;
		this.stockType=builder.stockType;
		this.lastDividend=builder.lastDividend;
		
	}

	 public StockBO(){
		 //default constructor called by the Mapper.
	 }
	
	/** Builder pattern implementation to build StockBO object. */
	  public static class StockBOBuilder {
		
			private String stockSymbol;
			private BigDecimal lastDividend;
			private BigDecimal parValue;
			private BigDecimal price;
			private StockType stockType;
			private BigDecimal fixedDividend;
			
			StockBOBuilder(){}
			
			/**
		     * Set stockSymbol
		     *
		     * @param stockSymbol stock symbol
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder stockSymbol(String stockSymbol) {
		      this.stockSymbol = stockSymbol;
		      return this;
		    }
		    
		    /**
		     * Set lastDividend
		     *
		     * @param lastDividend stock lastDividend
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder lastDividend(BigDecimal lastDividend) {
		      this.lastDividend = lastDividend;
		      return this;
		    }
		    
		    /**
		     * Set parValue
		     *
		     * @param parValue stock parValue
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder parValue(BigDecimal parValue) {
		      this.parValue = parValue;
		      return this;
		    }
		    
		    
		    /**
		     * Set stock price
		     *
		     * @param price stock price
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder price(BigDecimal price) {
		      this.price = price;
		      return this;
		    }
		    
		    /**
		     * Set stockType
		     *
		     * @param stockType Type of stock
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder stockType(StockType stockType) {
		      this.stockType = stockType;
		      return this;
		    }
		    
		    /**
		     * Set fixedDividend
		     *
		     * @param stockfixedDividend fixedDividend of stock
		     * @return StockBOBuilder
		     */
		    public StockBOBuilder stockType(BigDecimal fixedDividend) {
		      this.fixedDividend = fixedDividend;
		      return this;
		    }
		    
		    /**
		     * Build the StockBO object by calling constructor in StockBO class.
		     *
		     * @return StockBO object.
		     */
		    public StockBO build() {

		      return new StockBO(this);
		    }
	  }
	

}
