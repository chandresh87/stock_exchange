/** */
package com.jp.stock.service.exception;

/**
 * A business service exception for the stock exchange.
 *
 * @author chandresh.mishra
 */
public class StockMarketServiceException extends RuntimeException {

  private static final long serialVersionUID = 1381320130022416598L;

  private String code;

  /**
   * Constructs exception with code, message and cause.
   *
   * @param code
   * @param message
   * @param rootCause
   */
  public StockMarketServiceException(String code, String message, Throwable rootCause) {
    super(message, rootCause);
    this.code = code;
  }

  /**
   * Call Runtime exception with message and cause.
   *
   * @param message
   * @param cause
   */
  protected StockMarketServiceException(String message, Throwable cause) {
    super(message, cause);
    this.code = "";
  }

  /** @return code */
  public String getCode() {
    return code;
  }

  /** Returns a string consisting of the name, code, message and cause for this exception. */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("StockMarketServiceException [code=")
        .append(code)
        .append("\nName : " + this.getClass().getSimpleName())
        .append("\nMessage : " + this.getMessage())
        .append("\nCause : " + this.getCause())
        .append("]");
    return builder.toString();
  }
}
