package com.fattahi.general.model;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FUser extends BaseEntity<Integer> implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean accountNonExpired = true;
	private Set<GrantedAuthority> authorities;
	private Boolean isLocked;
	private String passWord;
	private String userName;
	private String token;

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@JsonIgnore
	public Boolean getIsLocked() {
		return isLocked;
	}

	@JsonIgnore
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	@JsonIgnore
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return getPassWord();
	}

	@Override
	public String getUsername() {
		return getUserName();
	}

	@Override
	public boolean isAccountNonLocked() {
		return getIsLocked();
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return isAccountNonExpired();
	}

	@JsonIgnore
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
