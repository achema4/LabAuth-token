package com.lab;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.Set;

import com.lab.service.ClientAuthService;
import com.lab.dto.TokenResponse;
import com.lab.dto.TokenRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@Path("/auth")
@ApplicationScoped
public class TokenResource {

    @Inject
    ClientAuthService authService;

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(TokenRequest request) {

        if (!authService.isValid(request.clientId(), request.clientSecret())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String jwt = Jwt.issuer("token-api")
                .subject(request.clientId())
                .groups(Set.of("service"))
                .expiresIn(Duration.ofHours(1))
                .sign();

        return Response.ok(new TokenResponse(jwt, 3600)).build();
    }

    @POST
    @Path("/test")
    public Response generateTest(TokenRequest request) {
        return Response.ok("ok").build();
    }

    @GET
    @Path("/prueba")
    public Response prueba() {
        return Response.ok("ok prueba").build();
    }
}
