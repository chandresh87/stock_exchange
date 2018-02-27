/**
 * 
 */
package com.jp.stock.api.model.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Error message class to be used for API response.
 * @author chandresh.mishra
 *
 */
@ToString
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -1L;

	@Getter
	@Setter
	private List<String> errors;
	@Getter
	@Setter
	private String msg;

	/** No-arg Constructor. */
	public ErrorMessage() {
	}

	/**
	 * Constructor to populate the message.
	 *
	 * @param message
	 */
	public ErrorMessage(String message) {
		this.msg = message;
	}

	/**
	 * Full argument constructor.
	 *
	 * @param msgCode
	 * @param msgDesc
	 */
	public ErrorMessage(String message, List<String> errors) {
		this.msg = message;
		this.errors = errors;
	}

}
