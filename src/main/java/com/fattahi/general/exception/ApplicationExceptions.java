package com.fattahi.general.exception;

import feign.Response;

/**
 * @author m.fattahi
 */
public class ApplicationExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String customMsg;
	private ExceptionEntity exceptionEntity = null;
	private String titleMsg;
	private ApplicationExceptionsTypes typeMsg;
	private Response response;

	public ApplicationExceptions() {

	}

	public ApplicationExceptions(String customMsg) {
		this.customMsg = customMsg;
		this.exceptionEntity = new ExceptionEntity(customMsg);
	}

	public ApplicationExceptions(String customMsg, Response response) {
		this.customMsg = customMsg;
		this.response = response;
		this.exceptionEntity = new ExceptionEntity(customMsg);
	}

	public ApplicationExceptions(ExceptionEntity exceptionEntity) {
		this.exceptionEntity = exceptionEntity;
	}

	public ApplicationExceptions(String customMsg, ApplicationExceptionsTypes typeMsg) {
		this.customMsg = customMsg;
		this.typeMsg = typeMsg;
		this.exceptionEntity = new ExceptionEntity(customMsg, typeMsg);
	}

	public ApplicationExceptions(String customMsg, ApplicationExceptionsTypes typeMsg, String titleMsg) {
		this.customMsg = customMsg;
		this.titleMsg = titleMsg;
		this.typeMsg = typeMsg;
		this.exceptionEntity = new ExceptionEntity(customMsg, typeMsg, titleMsg);
	}

	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}

	public ExceptionEntity getExceptionEntity() {
		return exceptionEntity;
	}

	public void setExceptionEntity(ExceptionEntity exceptionEntity) {
		this.exceptionEntity = exceptionEntity;
	}

	public String getTitleMsg() {
		return titleMsg;
	}

	public void setTitleMsg(String titleMsg) {
		this.titleMsg = titleMsg;
	}

	public ApplicationExceptionsTypes getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(ApplicationExceptionsTypes typeMsg) {
		this.typeMsg = typeMsg;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public static ExceptionEntity getEntityException(Exception e) {
		if (e.getCause() instanceof ApplicationExceptions) {
			return ((ApplicationExceptions) e.getCause()).getExceptionEntity();
		}
		return null;
	}

}