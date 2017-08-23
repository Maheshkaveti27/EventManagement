package com.event.management.resource;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter {

	   @Override
	   public void filter(final ContainerRequestContext requestContext,
	                      final ContainerResponseContext cres) throws IOException {
	      cres.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
	      // cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, access_token, authorization");
	      cres.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,auth_token");
	      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
	      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	      cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	   }
}