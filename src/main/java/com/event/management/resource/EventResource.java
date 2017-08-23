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
import com.event.management.entity.Event;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.EventService;
import com.event.management.util.AuthenticationUtil;

@Path("/events")
@Produces("application/json")
public class EventResource {

	@POST
	@Path("/save")
	@Produces("application/json")
	public Response saveCustomer(Event event, @HeaderParam("auth_token") String accessToken) {
		EventService service = SerializedSingleton.getEventServiceInstance();
		event.setEnabled(1);
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		String msg = service.saveEvent(event);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@GET
	@Produces("application/json")
	public Response getAllEvents(@HeaderParam("auth_token") String accessToken) {
		EventService service = SerializedSingleton.getEventServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		List<Event> events = service.getAllEvents();
		return Response.status(Response.Status.OK).entity(events).build();
	}

	@PUT
	@Path("{eventid}")
	@Produces("application/json")
	public Response updateEventDetails(Event event, @PathParam("eventid") int eventId,
			@HeaderParam("auth_token") String accessToken) {
		EventService service = SerializedSingleton.getEventServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		event.setEventId(eventId);
		int count = service.updateEventDetails(event);
		if (count > 0)
			return Response.status(Response.Status.OK).entity(new StatusResponse("Event Details Updated Successfully"))
					.build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("Event details Updated Failed")).build();
	}

	@DELETE
	@Path("{eventid}")
	@Produces("application/json")
	public Response deleteEvent(@PathParam("eventid") int eventId, @HeaderParam("auth_token") String accessToken) {
		EventService service = SerializedSingleton.getEventServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		int count = service.deleteEvent(eventId);
		if (count > 0)
			return Response.status(Response.Status.OK).entity(new StatusResponse("Event Details Deleted Successfully"))
					.build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("Event details delete Failed")).build();
	}
}