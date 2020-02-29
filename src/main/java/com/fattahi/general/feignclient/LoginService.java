package com.fattahi.general.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "uaa-server", url = "${oauth.url}/uaa")
public interface LoginService {

	@RequestMapping(method = RequestMethod.POST, value = "oauth/token")
	Object login(@RequestHeader("Authorization") String authorization, @RequestParam("grant_type") String grant_type,
			@RequestParam("username") String username, @RequestParam("password") String password);

	@RequestMapping(method = RequestMethod.DELETE, value = "/logout1")
	void logout(@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.GET, value = "/uac/ws/usersetting/uacuser/isChangePassword")
	Boolean isChangePassword(@RequestHeader("Authorization") String authorization);

}