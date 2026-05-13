package com.e6.interfaces.rest;
import com.e6.application.usecase.auth.GetUserProfileUseCase;
import com.e6.application.usecase.auth.ValidateAuthUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    ValidateAuthUseCase validateAuthUseCase;

    @Inject
    GetUserProfileUseCase getUserProfileUseCase;

    @GET
    public Response validate() {
        return Response.ok(validateAuthUseCase.execute()).build();
    }

    @GET
    @Path("/profile")
    public Response getProfile() {
        return Response.ok(getUserProfileUseCase.execute()).build();
    }
}