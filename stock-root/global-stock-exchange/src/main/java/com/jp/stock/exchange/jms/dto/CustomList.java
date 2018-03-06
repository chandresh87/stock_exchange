package com.jp.stock.exchange.jms.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * It is work around for the JMS converter. As converter is not able to convert to generic list.
 *
 * @author chandresh.mishra
 */
public class CustomList extends ArrayList<StockDTO> {

  public CustomList(Collection<StockDTO> c) {
    super(c);
  }

  // Empty constructor needed by Jackson.
  public CustomList() {}
}
