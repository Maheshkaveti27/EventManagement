package com.event.management.resource;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.event.management.DAO.AuthorizationToken;
import com.event.management.entity.CompanyInfo;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.CompanyDetails;
import com.event.management.model.StatusResponse;
import com.event.management.service.CompanyInfoService;
import com.event.management.util.AuthenticationUtil;

@Path("/companyinfo")
public class CompanyInfoResource {

	@POST
	@Produces("application/json")
	public Response saveCompanyInfo(CompanyInfo companyInfo, @HeaderParam("auth_token") String accessToken)
			throws NamingException {
		CompanyInfoService service = SerializedSingleton.getCompanyInfoServiceInstance();
		companyInfo.setEnabled(1);
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		String msg = service.saveCompanyInfo(companyInfo);
		return Response.status(Response.Status.OK).entity(new StatusResponse(msg)).build();
	}

	@GET
	@Produces("application/json")
	public Response getCompanyInfo(@HeaderParam("auth_token") String accessToken) {
		CompanyInfoService service = SerializedSingleton.getCompanyInfoServiceInstance();
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.CREATED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();

		CompanyDetails companyInfo = service.getCompanyInfo();
		return Response.status(Response.Status.OK).entity(companyInfo).build();
	}

	@PUT
	@Path("/{companyid}")
	@Produces("application/json")
	public Response updateCompanyInfo(CompanyInfo companyInfo, @PathParam("companyid") int companyId,
			@HeaderParam("auth_token") String accessToken) throws NamingException {
		CompanyInfoService service = SerializedSingleton.getCompanyInfoServiceInstance();
		companyInfo.setEnabled(1);
		AuthorizationToken authorizationToken = AuthenticationUtil.checkTokenValidOrNot(accessToken);
		if (authorizationToken == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity(new StatusResponse("Invalid token/Token expired"))
					.build();
		companyInfo.setCompanyId(companyId);
		int count = service.updateCompanyInfo(companyInfo);
		if (count > 0)
			return Response.status(Response.Status.OK)
					.entity(new StatusResponse("company Details Updated Successfully")).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new StatusResponse("COpmany details Updated Failed")).build();
	}

}