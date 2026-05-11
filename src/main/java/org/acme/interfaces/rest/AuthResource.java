package org.acme.interfaces.rest;

import org.acme.application.usecase.ValidateAuthUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    ValidateAuthUseCase validateAuthUseCase;

    @GET
    @Path("/validate")
    public Response validar(@HeaderParam("Authorization") String authHeader) {
        return validateAuthUseCase.execute(authHeader);
    }
}