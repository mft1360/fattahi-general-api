package com.fattahi.general.service;

import com.fattahi.general.model.FUser;

public interface ICurrentUser {

	FUser currentUser();

	String getToken();

	String[] getAuthorities();

	Boolean hasRole(String role);
	
	boolean isAdmin();
}
