package com.fattahi.general.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fattahi.general.exception.ApplicationExceptions;
import com.fattahi.general.exception.ApplicationExceptionsFeignClient;
import com.fattahi.general.exception.BusinessException;
import com.fattahi.general.exception.ExceptionEntity;

/**
 * The order is specified to tell spring that first check the general exceptions
 * which are the subclasses of BusinessException and if there was no match then
 * check this handler.
 *
 * Spring MessageSource could be used here to return message if needed.
 *
 *
 * @author mohsenfattahi81@gmail.com
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessExceptionHandler.class);

	@Value("${dublicate.record}")
	private String dublicateRecord;

	@Value("${delete.record}")
	private String deleteRecord;

	@Value("${error}")
	private String error;

	@Value("${access.denied}")
	protected String accessDenied;

	@ExceptionHandler({ BusinessException.class })
	protected ResponseEntity<Object> handleInvalidRequest(BusinessException exception, WebRequest request) {
		LOGGER.error("BusinessException or to be exact, DataAccessException!", exception);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(exception, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> unexpectedException(Exception exception, WebRequest request) {
		LOGGER.error("UnexpectedException!", exception);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String msg = null;
		try {
			if (exception.getCause().getCause().toString().contains("unique constraint")) {
				msg = dublicateRecord;
			} else if (exception.getCause().getCause().toString().contains("child record")) {
				msg = deleteRecord;
			} else {
				msg = error;
			}
		} catch (Exception e) {
			msg = error;
		}
		return handleExceptionInternal(exception, new ExceptionEntity(msg), headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException exception,
			WebRequest request) {
		LOGGER.error("DataIntegrityViolationException!", exception);
		String msg = null;
		if (exception.getCause().getCause().toString().contains("unique constraint")) {
			msg = dublicateRecord;
		} else if (exception.getCause().getCause().toString().contains("child record")) {
			msg = deleteRecord;
		} else {
			msg = error;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(exception, new ExceptionEntity(msg), headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> dataIntegrityViolationException(DataIntegrityViolationException exception,
			WebRequest request) {
		LOGGER.error("DataIntegrityViolationException!", exception);
		String msg = null;
		if (exception.getCause().getCause().toString().contains("unique constraint")) {
			msg = dublicateRecord;
		} else if (exception.getCause().getCause().toString().contains("child record")) {
			msg = deleteRecord;
		} else {
			msg = error;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(exception, new ExceptionEntity(msg), headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler({ ApplicationExceptionsFeignClient.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> feignException(ApplicationExceptionsFeignClient exception, WebRequest request) {
		LOGGER.error("feignException!", exception);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String msg = "";
		try {
			ObjectNode node = new ObjectMapper().readValue(
					exception.getExceptionEntity().getCustomMsg().split("content:")[1].trim(), ObjectNode.class);
			if (node.has("customMsg")) {
				msg = node.get("customMsg").toString().replaceAll("\"", "");
			}
		} catch (Exception e1) {
			msg = error;
		}
		return handleExceptionInternal(exception, new ExceptionEntity(msg), headers, HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseBody
	public ExceptionEntity handleBaseException(AccessDeniedException ex) {
		return new ExceptionEntity(accessDenied);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ApplicationExceptions.class)
	@ResponseBody
	public ExceptionEntity handleApplicationExceptions(ApplicationExceptions ex) {
		return ex.getExceptionEntity();
	}
}
