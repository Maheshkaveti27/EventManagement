package com.event.management.util;

import com.event.management.DAO.AuthTokenStore;
import com.event.management.DAO.AuthorizationToken;
import com.event.management.factory.SerializedSingleton;

public class AuthenticationUtil {
	
	public static AuthorizationToken checkTokenValidOrNot(String accessToken) {
		AuthTokenStore authTokenStore = SerializedSingleton.getAuthTokenStoreInstance();
		   AuthorizationToken authorizationToken = authTokenStore.retrieveToken(accessToken);
		return authorizationToken;
	}
	
	public static AuthorizationToken removeToken(String accessToken) {
		AuthTokenStore authTokenStore = SerializedSingleton.getAuthTokenStoreInstance();
		   AuthorizationToken authorizationToken = authTokenStore.removeToken(accessToken);
		return authorizationToken;
	}

}
