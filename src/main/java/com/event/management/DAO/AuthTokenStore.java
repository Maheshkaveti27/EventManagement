package com.event.management.DAO;

/**
 * @author saitejamacharla
 *
 */
public interface AuthTokenStore {
	
	/**
	 * Stores the given token 
	 * @param token
	 */
	public void storeToken(AuthorizationToken token);
	
	/**
	 * Fetches the Token Object for given token value. If the token is not available returns null
	 * @param token
	 * @return
	 */
	public AuthorizationToken retrieveToken(String token);
	
	
	/**
	 * Remove the user Token 
	 * @param token
	 * @return
	 */
	public AuthorizationToken removeToken(String token);

	/**
	 * clears all expired tokens
	 */
	public void clearExpiredTokens();

}
