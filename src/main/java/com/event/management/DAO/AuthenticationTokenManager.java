package com.event.management.DAO;

import com.event.management.entity.User;
import com.event.management.factory.SerializedSingleton;

/**
 * @author saitejamacharla
 * 
 */
public final class AuthenticationTokenManager {

	private AuthTokenStore authTokenStore;
	private LoginDAO loginDAO;
	public AuthenticationTokenManager() {
		authTokenStore = SerializedSingleton.getAuthTokenStoreInstance();
		loginDAO = SerializedSingleton.getLoginDAOInstance();
	}

	public AuthorizationToken authenticate(User appUserExt) {
		User user = loginDAO.authenticate(appUserExt);
		AuthorizationToken authorizationToken = null;
		if (user != null) {
			authorizationToken = new AuthorizationToken(user);
			authTokenStore.storeToken(authorizationToken);
		}
		return authorizationToken;
	}

	/**
	 * Validates the User Token and return boolean value
	 * 
	 * @param userSuppliedtoken
	 * @return
	 */
	public boolean isAuthTokenValid(String userSuppliedtoken) throws Exception{

		if (userSuppliedtoken == null) {
			throw new Exception("EMpty TOken");
		}

		AuthorizationToken authorizationToken = authTokenStore.retrieveToken(userSuppliedtoken);

		if (authorizationToken != null) {
			if (authorizationToken.hasExpired()) {
				authTokenStore.removeToken(userSuppliedtoken);
				throw new Exception("Expired TOken");
			} else {
				authorizationToken.resetExpirationDate();
				return true;
			}
		}

		return false;

	}

	/**
	 * @param userSuppliedtoken
	 */
	public void removeToken(String userSuppliedtoken) {
		authTokenStore.removeToken(userSuppliedtoken);

	}

}
