package com.jp.stock.service.exception.code;

/**
 * Exception code enumeration for service exceptions.
 *
 * @author chandresh.mishra
 */
public enum BusinessErrorCode {
  JP001B("JP001B", "Business Service Exception with 001 code"),
  JP002B("JP002B", "Forbidden Business Service Exception"),
  JP003B("JP003B", "Business Validation Exception");

  private String description;
  private String id;

  private BusinessErrorCode(String id, String description) {
    this.id = id;
    this.description = description;
  }

  /** @return description */
  public String getDescription() {
    return description;
  }

  /** @return id */
  public String getId() {
    return id;
  }
}
