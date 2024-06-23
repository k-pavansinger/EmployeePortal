package com.onedigitals.EmployeePortal.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.security.Principal;

@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {
	private static final String KEY = "secret";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String path = requestContext.getUriInfo().getPath();
		if (path.equals("login") ||path.equals("register") ) {
			// If the request is for the login endpoint, don't apply the filter
			return;
		}

		String authHeader = requestContext.getHeaderString("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring("Bearer ".length());

			try {
				DecodedJWT jwt = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);

				Claim roleClaim = jwt.getClaim("role");
				Claim nameClaim = jwt.getClaim("name");

				if (!roleClaim.isNull() && !nameClaim.isNull()) {
					String role = roleClaim.asString();
					String name = nameClaim.asString();

					SecurityContext securityContext = new SecurityContext() {
						@Override
						public Principal getUserPrincipal() {
							return () -> name;
						}

						@Override
						public boolean isUserInRole(String role) {
							return role.equals(role);
						}

						@Override
						public boolean isSecure() {
							return false;
						}

						@Override
						public String getAuthenticationScheme() {
							return "Bearer";
						}
					};

					requestContext.setSecurityContext(securityContext);
				} else {
					// If the role or name claim is missing, abort the request
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
					return;
				}
			} catch (Exception e) {
				// If the token is invalid (e.g., signature doesn't match, token is expired,
				// etc.), abort the request
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				return;
			}
		} else {
			// If the Authorization header is missing or doesn't start with "Bearer ", abort
			// the request
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			return;
		}

		// If everything is OK, continue with the request
	}
}
