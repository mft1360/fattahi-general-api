package com.fattahi.general.utility;

import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientConfiguration implements RequestInterceptor {


	@Override
	public void apply(RequestTemplate template) {
		template.header("", "");
	}
}