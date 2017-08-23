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
import com.event.management.entity.Customer;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.StatusResponse;
import com.event.management.service.CustomerService;
import com.event.management.util.AuthenticationUtil;

@Path("/customers")
public class CustomerResource {

	@POST
	@Path("/save")
	@Produces("application/json")
	public Response saveCustomer(Customer customer, @HeaderParam("auth_token") String accessToken) {
		CustomerService service = SerializedSingleton.getCustomerServiceInstance();
		customer.setEnabled(1);

		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		String msg = service.saveCustomer(customer);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@GET
	@Produces("application/json")
	public Response getAllCustomers(@HeaderParam("auth_token") String accessToken) {
		CustomerService service = SerializedSingleton.getCustomerServiceInstance();

		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		List<Customer> customers = service.getAllCustomers();
		return Response.status(Response.Status.OK).entity(customers).build();
	}

	@PUT
	@Path("{customerno}")
	@Produces("application/json")
	public Response updateCustomerDetails(Customer customer, @PathParam("customerno") int customerNo,
			@HeaderParam("auth_token") String accessToken) {
		CustomerService service = SerializedSingleton.getCustomerServiceInstance();

		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		customer.setCustomerNo(customerNo);
		int count = service.updateCustomerDetails(customer);
		if (count > 0)
			return Response.status(Response.Status.OK)
					.entity(new StatusResponse("Customer Details Updated Successfully")).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("Customer details Updated Failed")).build();
	}

	@DELETE
	@Path("{customerno}")
	@Produces("application/json")
	public Response deleteCustomer(@PathParam("customerno") int customerNo,
			@HeaderParam("auth_token") String accessToken) {
		CustomerService service = SerializedSingleton.getCustomerServiceInstance();

		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		int count = service.deleteCustomer(customerNo);
		if (count > 0)
			return Response.status(Response.Status.OK)
					.entity(new StatusResponse("Customer Details Deleted Successfully")).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("Customer details delete Failed")).build();
	}

}
