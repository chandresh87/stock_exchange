/**
 * 
 */
package com.jp.stock.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * This class holds the unique sequence data for the UniqueSequence Gemfire region. The unique value is used as a trade key.
 * 
 * @author chandresh.mishra
 * 
 */
@Region("UniqueSequence")
@ToString
public class UniqueSequence implements Serializable {

	private static final long serialVersionUID = 3209342518270638003L;

	@Id
	@Getter
	@Setter
	int id;
	
	@Getter
	@Setter
	int value;

	@PersistenceConstructor
	public UniqueSequence(int id, int value) {
		super();
		this.id = id;
		this.value = value;
	}

	
}
