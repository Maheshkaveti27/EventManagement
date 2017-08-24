package com.event.management.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.User;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.UserService;
import com.event.management.util.AuthenticationUtil;

@Path("/users")
public class UserResource {

	@POST
	@Path("/save")
	@Produces("application/json")
	public Response saveEventPlanner(User user, @HeaderParam("auth_token") String accessToken)
			throws MissingFileException {
		UserService service = SerializedSingleton.getUserServiceInstance();
		user.setPassword("123456");
		user.setEnabled(1);
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new StatusResponse("Invalid token/Token expired")).build();
		String msg;
		msg = service.SaveEventPlanner(user);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@POST
	@Path("/changepassword")
	@Produces("application/json")
	public Response changePassword(User user, @HeaderParam("auth_token") String accessToken) {
		UserService service = SerializedSingleton.getUserServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new StatusResponse("Invalid token/Token expired")).build();

		int count = service.updatePassword(user);
		if (count > 0)
			return Response.status(Response.Status.OK).entity(new StatusResponse("Password Updated Successfully"))
					.build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("Password Updated Failed")).build();
	}

	@GET
	@Produces("application/json")
	public Response getEventPanners(@HeaderParam("auth_token") String accessToken) {
		UserService service = SerializedSingleton.getUserServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new StatusResponse("Invalid token/Token expired")).build();

		List<User> users = service.getEventPanners();
		return Response.status(Response.Status.OK).entity(users).build();
	}

	@GET
	@Path("{token}")
	@Produces("application/json")
	public Response getEventPannerByUsername(@HeaderParam("auth_token") String accessToken,
			@PathParam("token") String token) {
		/*
		 * AuthorizationToken authorizationToken =
		 * AuthenticationUtil.checkTokenValidOrNot(accessToken);
		 * if(authorizationToken == null) return
		 * Response.status(Response.Status.CREATED) .entity(new
		 * StatusResponse("Invalid token/Token expired")).build();
		 */

		// User user = service.getEventPannerByUsername(username);
		AuthorizationToken authorizationToken = SerializedSingleton.getAuthTokenStoreInstance().retrieveToken(token);
		if (authorizationToken == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new StatusResponse("User Details not found"))
					.build();
		} else {
			return Response.status(Response.Status.OK).entity(authorizationToken).build();
		}
	}

	@PUT
	@Path("{username}")
	@Produces("application/json")
	public Response updateEventPlannerDetails(User user, @PathParam("username") String username,
			@HeaderParam("auth_token") String accessToken) {
		UserService service = SerializedSingleton.getUserServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new StatusResponse("Invalid token/Token expired")).build();
		user.setUsername(username);
		int count = service.updateEventPlannerDetails(user);
		if (count > 0)
			return Response.status(Response.Status.OK).entity(new StatusResponse("User Details Updated Successfully"))
					.build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("User details Updated Failed")).build();
	}

	@DELETE
	@Path("{username}")
	@Produces("application/json")
	public Response deleteEventPlanner(@PathParam("username") String username,
			@HeaderParam("auth_token") String accessToken) {
		UserService service = SerializedSingleton.getUserServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new StatusResponse("Invalid token/Token expired")).build();

		int count = service.deleteEventPlanner(username);
		if (count > 0)
			return Response.status(Response.Status.OK).entity(new StatusResponse("User Details Deleted Successfully"))
					.build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("User details delete Failed")).build();
	}

	@POST
	@Path("/logout")
	@Produces("application/json")
	public Response deleteEventPlanner(@HeaderParam("auth_token") String accessToken) {
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.CREATED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		else
			AuthenticationUtil.removeToken(accessToken);

		return Response.status(Response.Status.OK).entity(new StatusResponse("logout Successfully")).build();
	}
}
