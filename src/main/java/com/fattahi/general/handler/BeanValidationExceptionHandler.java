package com.fattahi.general.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fattahi.general.exception.BeanValidationException;
import com.fattahi.general.exception.ExceptionEntity;

/**
 *
 * @author mohsenfattahi81@gmail.com
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BeanValidationExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanValidationExceptionHandler.class);

	@Value("${error}")
	private String error;

	@ExceptionHandler({ BeanValidationException.class })
	protected ResponseEntity<Object> handleInvalidRequest(BeanValidationException exception, WebRequest request) {
		LOGGER.warn("BEAN VALIDATION FAILED! {}", exception.getMessage());
		List<ErrorResource> errorResources = null;
		List<List<ErrorResource>> errorResourcesList = new ArrayList<>();
		List<BindingResult> results = exception.getBindingResults();
		if (results == null) {
			results = new ArrayList<BindingResult>();
			results.add(exception.getBindingResult());
		}
		for (BindingResult br : results) {
			errorResources = new ArrayList<>();
			for (FieldError error : br.getFieldErrors()) {
				ErrorResource errorResource = new ErrorResource();
				errorResource.setField(error.getField());
				errorResource.setMessage(error.getDefaultMessage());
				errorResources.add(errorResource);
			}
			errorResourcesList.add(errorResources);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(exception,
				new ExceptionEntity(exception.getBindingResults() == null ? errorResources : errorResourcesList, error),
				headers, HttpStatus.BAD_REQUEST, request);
	}

	public class ErrorResource {

		private String field;
		private String message;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}
