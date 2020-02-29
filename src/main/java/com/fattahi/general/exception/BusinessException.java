package com.fattahi.general.exception;

/**
 * This is the Base Exception to be extended by general exceptions. Genera
 * exceptions are the ones which are created to be extended by specific
 * exceptions to categorize the exceptions and produce the same HTTP status code
 * for them instead of handling each specific exception. Specific exceptions are
 * designed to extend general exceptions and make the code meaningful.
 *
 *
 * Ideally, all the exceptions of Spring data which exist in the exception
 * haiarchy should be translated to one general exception and be translated in
 * the service layer.
 *
 * @author mohsenfattahi81@gmail.com
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
