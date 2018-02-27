/** */
package com.jp.stock.entity;

import java.io.Serializable;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;

/**
 * This class holds the unique sequence data for the UniqueSequence Gemfire region. The unique value
 * is used as a trade key.
 *
 * @author chandresh.mishra
 */
@Region("UniqueSequence")
@ToString
public class UniqueSequence implements Serializable {

  private static final long serialVersionUID = 3209342518270638003L;

  @Id int id;

  int value;

  @PersistenceConstructor
  public UniqueSequence(int id, int value) {
    super();
    this.id = id;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
