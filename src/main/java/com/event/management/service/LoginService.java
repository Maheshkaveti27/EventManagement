package com.event.management.service;

import com.event.management.DAO.AuthenticationTokenManager;
import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.User;
import com.event.management.factory.SerializedSingleton;

public class LoginService {
	private AuthenticationTokenManager authTokenManager;
	
	public LoginService() {
		authTokenManager = SerializedSingleton.getAuthTokenManager();
	}

	public AuthorizationToken authenticateUser(User user) {
		return authTokenManager.authenticate(user);
	}

}
