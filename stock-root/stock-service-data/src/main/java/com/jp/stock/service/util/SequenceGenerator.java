/** */
package com.jp.stock.service.util;

import com.jp.stock.dao.UniqueSequenceDao;
import com.jp.stock.entity.UniqueSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility for getting the unique sequence for using as a key value
 *
 * @author chandresh.mishra
 */
@Component
public class SequenceGenerator {

  /** Autowired uniqueSequence database service */
  @Autowired UniqueSequenceDao uniqueSequenceDao;

  /** @return int The unique sequence starting from 1 */
  public int getUniqueSequence() {

    UniqueSequence currentSequence = uniqueSequenceDao.findById(1).orElseGet(() -> buildSequence());

    int value = currentSequence.getValue();
    currentSequence.setValue(value + 1);

    uniqueSequenceDao.save(currentSequence);
    return value;
  }

  UniqueSequence buildSequence() {
    UniqueSequence sequence = new UniqueSequence(1, 1);
    uniqueSequenceDao.save(sequence);
    return sequence;
  }
}
