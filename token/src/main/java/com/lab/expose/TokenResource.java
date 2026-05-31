package com.lab.expose;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.Set;

import com.lab.service.ClientAuthService;
import com.lab.dto.TokenResource;
import com.lab.dto.TokenRequest;


@Path("/token")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {

    private final ClientAuthService authService;

    public TokenResource(ClientAuthService authService) {
        this.authService = authService;
    }

    @POST
    public Response generate(TokenRequest request) {

        if (!authService.isValid(request.clientId(), request.clientSecret())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        String jwt = Jwt.issuer("token-api")
                .subject(request.clientId())
                .groups(Set.of("service"))
                .expiresIn(Duration.ofHours(1))
                .sign();

        return Response.ok(
                new TokenResponse(jwt, 3600)
        ).build();
    }
}
