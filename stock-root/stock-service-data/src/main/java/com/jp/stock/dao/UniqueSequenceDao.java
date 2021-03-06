package com.jp.stock.dao;

import com.jp.stock.entity.UniqueSequence;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * It performs the CRUD operation on UniqueSequence region by extending Spring data CrudRepository
 *
 * <p>The main purpose of this region is to generate the unique key for Trade. This unique key is
 * used as a primary key in trade.
 *
 * @author chandresh.mishra
 */
@Repository
public interface UniqueSequenceDao extends CrudRepository<UniqueSequence, Integer> {

  /** Saves/update Unique Sequence record in gemfire region */
  @SuppressWarnings("unchecked")
  public UniqueSequence save(UniqueSequence sequence);

  /** Get Unique Sequence record for a given key from gemfire region */
  public Optional<UniqueSequence> findById(Integer id);
}
