package com.fattahi.general.exception;

import feign.Response;

/**
 * @author m.fattahi
 */
public class ApplicationExceptionsFeignClient extends ApplicationExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationExceptionsFeignClient(String customMsg, Response response) {
		super(customMsg, response);
	}

}