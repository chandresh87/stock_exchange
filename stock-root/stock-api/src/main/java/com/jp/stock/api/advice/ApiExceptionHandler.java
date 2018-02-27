/**
 * 
 */
package com.jp.stock.api.advice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jp.stock.api.model.response.ErrorMessage;

import com.jp.stock.service.exception.StockMarketServiceException;

@org.springframework.web.bind.annotation.ControllerAdvice 
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * Exception Method handler for type ConstraintViolationException class.
	 *
	 * @param ex-ConstraintViolationException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Map<String, Object>> handleValidationFailure(ConstraintViolationException ex) {

		final Map<String, Object> response = new HashMap<>();
		response.put("message", "Your request contains errors");
		response.put("errors", ex.getMessage());

		return ResponseEntity.badRequest().body(response);
	}
	
	/**
	 * Exception method handler for StockMarketServiceException class.
	 *
	 * @param ex
	 *            - StockMarketServiceException
	 * @param webRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = { StockMarketServiceException.class })
	protected ResponseEntity<Object> bussinessException(StockMarketServiceException ex,
			WebRequest webRequest) {
		logger.error(ex);
		if (null != ex.getCause()) {
			List<String> errors = new ArrayList<>();
			errors.add(ex.getCause().getMessage());
			return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), errors), HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * This method will catch BAD_REQUEST exceptions thrown during the
	 * validation phase.
	 *
	 * @param ex
	 *            the exception
	 * @param headers
	 *            the headers to be written to the response
	 * @param status
	 *            the selected response status
	 * @param request
	 *            the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(ex);
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
		ex.getBindingResult().getGlobalErrors()
				.forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
		Collections.sort(errors);
		return new ResponseEntity<>(new ErrorMessage("Bad Request", errors), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Exception Method handler for type Exception class.
	 *
	 * @param ex
	 *            - Exception
	 * @param webRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> genericException(Exception ex, WebRequest webRequest) {
		logger.error(ex);
		String responseBody = "Something went wrong. Please try after sometime";
		return new ResponseEntity<>(new ErrorMessage(responseBody), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
