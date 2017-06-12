package com.simple.core.model;

import java.io.Serializable;
import java.util.List;

import com.simple.core.entity.Authority;
import com.simple.core.entity.User;

public class LoginInfoModel implements Serializable {

	private static final long serialVersionUID = 5828941298917496823L;

	private User user;
	private List<Authority> authoritys;

	public User getUser() {
		return user;
	}

	public LoginInfoModel setUser(User user) {
		this.user = user;
		return this;
	}

	public List<Authority> getAuthoritys() {
		return authoritys;
	}

	public LoginInfoModel setAuthoritys(List<Authority> authoritys) {
		this.authoritys = authoritys;
		return this;
	}

}
