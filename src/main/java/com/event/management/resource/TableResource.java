package com.event.management.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.Table;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.TableService;
import com.event.management.util.AuthenticationUtil;

@Path("/tables")
public class TableResource {
	
	@GET
	@Produces("application/json")
	public Response getTablesDetails(@HeaderParam("auth_token") String accessToken) {
		TableService service = SerializedSingleton.getTableServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		 
		 List<Table> tables = service.getTablesDetails();
		 return Response.status(Response.Status.OK)
						.entity(tables).build();
	}
	
	@GET
	@Path("/{tableid}")
	@Produces("application/json")
	public Response getTableDetails(@HeaderParam("auth_token") String accessToken, @PathParam("tableid") int tableId) {
		TableService service = SerializedSingleton.getTableServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		 
		 Table table = service.getTableDetails(tableId);
		 return Response.status(Response.Status.OK)
						.entity(table).build();
	}

}
