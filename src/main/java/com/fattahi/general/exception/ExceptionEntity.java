package com.fattahi.general.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ExceptionEntity {

	private String customMsg;
	private String title;
	private ApplicationExceptionsTypes type;
	private Object errorList;

	public ExceptionEntity() {
	}

	public ExceptionEntity(String customMsg) {
		this.customMsg = customMsg;
	}

	public ExceptionEntity(Object errorList) {
		this.errorList = errorList;
	}

	public ExceptionEntity(Object errorList, String customMsg) {
		this.errorList = errorList;
		this.customMsg = customMsg;
	}

	public ExceptionEntity(String customMsg, ApplicationExceptionsTypes type) {
		this.customMsg = customMsg;
		this.type = type;
	}

	public ExceptionEntity(String customMsg, ApplicationExceptionsTypes type, String title) {
		this.customMsg = customMsg;
		this.type = type;
		this.title = title;
	}

	@JsonInclude(Include.NON_NULL)
	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}

	@JsonInclude(Include.NON_NULL)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonInclude(Include.NON_NULL)
	public ApplicationExceptionsTypes getType() {
		return type;
	}

	public void setType(ApplicationExceptionsTypes type) {
		this.type = type;
	}

	@JsonInclude(Include.NON_NULL)
	public Object getErrorList() {
		return errorList;
	}

	public void setErrorList(Object errorList) {
		this.errorList = errorList;
	}

}
