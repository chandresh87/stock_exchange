/**
 * 
 */
package com.jp.stock.api.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chandresh.mishra
 *
 */
@ToString
public class Message {
	
	@Getter
	@Setter
	private BigDecimal result;
	
	@Getter
	@Setter
	private String responseMessgae;

}
