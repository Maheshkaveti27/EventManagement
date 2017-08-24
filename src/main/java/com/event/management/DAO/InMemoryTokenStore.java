package com.event.management.DAO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores the tokens in memory using <code>Map</code> Object
 * 
 * @author saitejamacharla
 * 
 */
public class InMemoryTokenStore implements AuthTokenStore {

	// An authentication token storage which stores <service_key, auth_token>.
	private final Map<String, AuthorizationToken> authorizationTokensStorage = new ConcurrentHashMap<String, AuthorizationToken>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.osius.cds.services.rest.v1.auth.AuthTokenStore#storeToken(com.osius
	 * .cds.services.rest.v1.auth.AuthorizationToken)
	 */
	@Override
	public void storeToken(AuthorizationToken authorizationToken) {
		authorizationTokensStorage.put(authorizationToken.getToken(), authorizationToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.osius.cds.services.rest.v1.auth.AuthTokenStore#retrieveToken(java
	 * .lang.String)
	 */
	@Override
	public AuthorizationToken retrieveToken(String token) {
		if(token == null)
			return null;
		AuthorizationToken authorizationToken = authorizationTokensStorage.get(token);
		if (authorizationToken == null || authorizationToken.hasExpired()) {
			return null;
		} else {
			System.out.println("not expired");
		}
		return authorizationTokensStorage.get(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.osius.cds.services.rest.v1.auth.AuthTokenStore#removeToken(java.lang
	 * .String)
	 */
	@Override
	public AuthorizationToken removeToken(String token) {
		return authorizationTokensStorage.remove(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.osius.cds.services.rest.v1.auth.AuthTokenStore#clearExpiredTokens()
	 */
	@Override
	public void clearExpiredTokens() {
		if (authorizationTokensStorage.size() > 0) {
			for (Map.Entry<String, AuthorizationToken> tokenMapEntry : authorizationTokensStorage.entrySet()) {

				if (tokenMapEntry.getValue().hasExpired()) {
					authorizationTokensStorage.remove(tokenMapEntry.getKey());
				}

			}
		}

	}

}
