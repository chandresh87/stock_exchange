/** */
package com.jp.stock.entity;

import java.io.Serializable;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

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

  @Id Integer id;

  Integer value;

  @PersistenceConstructor
  public UniqueSequence(Integer id, Integer value) {
    super();
    this.id = id;
    this.value = value;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
