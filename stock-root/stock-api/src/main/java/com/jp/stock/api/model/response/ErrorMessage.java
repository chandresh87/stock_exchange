/** */
package com.jp.stock.api.model.response;

import java.io.Serializable;
import java.util.List;
import lombok.ToString;

/**
 * Error message class to be used for API response.
 *
 * @author chandresh.mishra
 */
@ToString
public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = -1L;

  private List<String> errors;
  private String msg;

  /** No-arg Constructor. */
  public ErrorMessage() {}

  /**
   * Constructor to populate the message.
   *
   * @param message
   */
  public ErrorMessage(String message) {
    this.msg = message;
  }

  /**
   * Full argument constructor.
   *
   * @param msgCode
   * @param msgDesc
   */
  public ErrorMessage(String message, List<String> errors) {
    this.msg = message;
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
