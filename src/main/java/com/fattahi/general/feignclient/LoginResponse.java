package com.fattahi.general.feignclient;

import lombok.Data;

@Data
public class LoginResponse {

	private String access_token;
	private String token_type;
	private Long expires_in;
	private String scope;
	private String isAdmin;

}
