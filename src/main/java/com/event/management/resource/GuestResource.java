package com.event.management.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.EventGuest;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.GuestNewTableDetails;
import com.event.management.model.StatusResponse;
import com.event.management.service.GuestService;
import com.event.management.util.AuthenticationUtil;

@Path("/guests")
@Produces("application/json")
public class GuestResource {

	@POST
	@Path("/save")
	@Produces("application/json")
	public Response saveEventGuest(EventGuest eventGuest, @HeaderParam("auth_token") String accessToken) {
		GuestService service = SerializedSingleton.getGuestServiceInstance();
		eventGuest.setEnabled(1);
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		String msg = service.saveEventGuest(eventGuest);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@GET
	@Path("{eventid}")
	@Produces("application/json")
	public Response getAllGuestsByEventId(@PathParam("eventid") int eventId,
			@HeaderParam("auth_token") String accessToken) {
		GuestService service = SerializedSingleton.getGuestServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		List<EventGuest> eventGuests = service.getAllGuestsByEventId(eventId);
		return Response.status(Response.Status.OK).entity(eventGuests).build();
	}

	@POST
	@Path("/{eventid}/generatesitting/{tableid}")
	@Produces("application/json")
	public Response generateSittingArrangment(@HeaderParam("auth_token") String accessToken,
			@PathParam("eventid") int eventId, @PathParam("tableid") int tableId) {
		GuestService service = SerializedSingleton.getGuestServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		String msg = service.generateSittingArrangment(eventId, tableId);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@GET
	@Path("/{eventid}/tablesdata")
	@Produces("application/json")
	public Response getEventTablesData(@HeaderParam("auth_token") String accessToken, @PathParam("eventid") int eventId) {
		GuestService service = SerializedSingleton.getGuestServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		Map<String, List<EventGuest>> guestTableDetails = service.getEventTablesData(eventId);
		return Response.status(Response.Status.OK).entity(guestTableDetails).build();
	}

	@PUT
	@Path("/{eventid}/updateguesttable")
	@Produces("application/json")
	public Response updateGuestTable(@HeaderParam("auth_token") String accessToken, @PathParam("eventid") int eventId,
			GuestNewTableDetails guestNewTableDetails) {
		GuestService service = SerializedSingleton.getGuestServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		guestNewTableDetails.setEventId(eventId);
		String msg = service.updateGuestTable(guestNewTableDetails);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}
}