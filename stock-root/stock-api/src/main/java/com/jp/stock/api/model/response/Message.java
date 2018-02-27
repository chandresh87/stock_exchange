/** */
package com.jp.stock.api.model.response;

import java.math.BigDecimal;
import lombok.ToString;

/** @author chandresh.mishra */
@ToString
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
