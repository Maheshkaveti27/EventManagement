package com.event.management.resource;

import javax.naming.NamingException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.User;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.LoginService;

@Path("/")
public class LoginResource {

	
	@POST
	@Path("login")
	@Produces("application/json")
	public Response authenticateUser(User user) throws NamingException {
		LoginService service = SerializedSingleton.getLoginServiceInstance();
		AuthorizationToken authorizationToken = service.authenticateUser(user);
		if(authorizationToken == null)
			return Response.status(Response.Status.NOT_FOUND)
					.entity(new StatusResponse("Invalid username/password")).build();
		return Response.status(Response.Status.OK)
				.entity(authorizationToken).build();
	}

	/**
	 * @param accessToken
	 */
	@POST
	@Path("logout")
	public void logout(@HeaderParam("auth_token") String accessToken) {
		SerializedSingleton.getAuthTokenManager().removeToken(accessToken);
	}
}