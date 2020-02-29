package com.fattahi.general.feignclient;

import lombok.Data;

@Data
public class LoginError {

	private String error;
	private String error_description;

}
