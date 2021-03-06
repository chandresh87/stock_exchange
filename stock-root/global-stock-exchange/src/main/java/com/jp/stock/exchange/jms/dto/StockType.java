package com.jp.stock.exchange.jms.dto;

/**
 * Enum for the stock type.
 *
 * @author chandresh.mishra
 */
public enum StockType {

  /** Indicates that a stock is common and the dividend yield is calculated with last dividend. */
  COMMON,

  /**
   * Indicates that a stock is preferred and the dividend yield is calculated with fixed dividend.
   */
  PREFERRED;
}
