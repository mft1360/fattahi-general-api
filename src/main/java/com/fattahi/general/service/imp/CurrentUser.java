package com.fattahi.general.service.imp;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import com.fattahi.general.model.FUser;
import com.fattahi.general.service.ICurrentUser;

@Component
@SuppressWarnings("unchecked")
public class CurrentUser implements ICurrentUser {

	@Override
	public FUser currentUser() {
		try {
			FUser user = new FUser();
			if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
						.getContext().getAuthentication();
				user = (FUser) userPassAuth.getPrincipal();
			} else {
				OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
						.getAuthentication();
				HashMap<String, Object> details = (HashMap<String, Object>) authentication.getUserAuthentication()
						.getDetails();
				if (details == null) {
					user.setUserName(authentication.getUserAuthentication().getPrincipal().toString());
				} else {
					HashMap<String, Object> principal = (HashMap<String, Object>) details.get("principal");
					user.setId(Integer.parseInt(principal.get("id").toString()));
					user.setUserName(principal.get("userName").toString());
				}
			}
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean isAdmin() {
		OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
				.contains("ROLE_ADMINUSER");
	}

	@Override
	public String getToken() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
					.getContext().getAuthentication();
			return "bearer " + ((FUser) userPassAuth.getPrincipal()).getToken();
		} else {
			OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
					.getAuthentication();
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			return details.getTokenType() + " " + details.getTokenValue();
		}

	}

	@Override
	public String[] getAuthorities() {
		return null;
	}

	@Override
	public Boolean hasRole(String role) {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken userPassAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
					.getContext().getAuthentication();
			FUser user = (FUser) userPassAuth.getPrincipal();
			for (GrantedAuthority auth : user.getAuthorities()) {
				if (auth.getAuthority().equals(role)) {
					return true;
				}
			}
			return false;
		} else {
			OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
					.getAuthentication();
			return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()).contains(role);
		}
	}
}
