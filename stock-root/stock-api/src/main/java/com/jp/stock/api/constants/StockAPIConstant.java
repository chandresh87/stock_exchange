package com.jp.stock.api.constants;

/**
 * constant class for the validations
 *
 * @author chandresh.mishra
 */
public class StockAPIConstant {

  public static final String ERROR_NOT_NULL = "Missing mandatory data";
  public static final String INVALID_NUMBER = "Number is not valid";

  private StockAPIConstant() {
    throw new IllegalStateException("Utility class");
  }
}
