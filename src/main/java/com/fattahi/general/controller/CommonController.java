package com.fattahi.general.controller;

import java.security.Principal;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fattahi.general.utility.ISerializableResourceBundleMessageSource;

/**
 * @author m.fatahi
 */
@RestController
@RequestMapping("/common")
public class CommonController {

	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	public Principal getCurrentUser(Principal user) {
		return user;
	}

	@Autowired(required = false)
	ISerializableResourceBundleMessageSource messageBundle;

	@RequestMapping(value = "/messageBundle", method = RequestMethod.GET)
	@ResponseBody
	public Properties messageBundle() {
		return messageBundle.getAllProperties();
	}
}
