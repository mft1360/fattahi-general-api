package com.fattahi.general.exception;

import org.springframework.context.annotation.Configuration;

import com.fattahi.general.exception.ApplicationExceptionsFeignClient;

import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		Exception exp = defaultErrorDecoder.decode(methodKey, response);
		return new ApplicationExceptionsFeignClient(exp.getMessage(), response);
	}
}
