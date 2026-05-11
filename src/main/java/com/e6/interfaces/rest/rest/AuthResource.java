package com.e6.interfaces.rest.rest;
import com.e6.domain.domain.model.User;
import com.e6.application.application.dto.UserProfileResponseDto;
import com.e6.application.application.dto.UserInfoResponseDto;
import com.e6.application.application.usecase.GetAuthenticatedUserUseCase;
import com.e6.application.application.usecase.ValidateAuthUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    ValidateAuthUseCase validateAuthUseCase;


    @Inject
    GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;



    @GET
    @Path("/validate")
    public Response validar(@HeaderParam("Authorization") String authHeader) {
        return validateAuthUseCase.execute(authHeader);
    }


    @GET
    @Path("/profile")
    public Response getProfile(@HeaderParam("Authorization") String authHeader) {
        User user = getAuthenticatedUserUseCase.execute(authHeader);

        return Response.ok(
                new UserProfileResponseDto(
                        user.getName(),
                        user.getRole().getName()
                )
        ).build();
    }

    @GET
    @Path("/email")
    public Response getEmail(@HeaderParam("Authorization") String authHeader) {
        User user = getAuthenticatedUserUseCase.execute(authHeader);

        return Response.ok(
                new UserInfoResponseDto(user.getEmail())
        ).build();
    }







}