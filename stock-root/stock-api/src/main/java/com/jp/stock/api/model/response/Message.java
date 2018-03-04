/** */
package com.jp.stock.api.model.response;

import java.math.BigDecimal;

/**
 * Response message class
 *
 * @author chandresh.mishra
 */
public class Message {

  private BigDecimal result;

  private String responseMessgae;

  public BigDecimal getResult() {
    return result;
  }

  public void setResult(BigDecimal result) {
    this.result = result;
  }

  public String getResponseMessgae() {
    return responseMessgae;
  }

  public void setResponseMessgae(String responseMessgae) {
    this.responseMessgae = responseMessgae;
  }
}
