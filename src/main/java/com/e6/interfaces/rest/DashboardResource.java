package com.e6.interfaces.rest;

import com.e6.application.dto.dashboard.CreateDashboardDTO;
import com.e6.application.usecase.dashboard.CreateDashboardUseCase;
import com.e6.application.usecase.dashboard.DeleteDashboardByIdUseCase;
import com.e6.application.usecase.dashboard.GetDashboardByIdUseCase;
import com.e6.application.usecase.dashboard.GetUserDashboardsUseCase;
import com.e6.domain.exception.DashboardNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    private final CreateDashboardUseCase createDashboardUseCase;
    private final GetUserDashboardsUseCase getUserDashboardsUseCase;
    private final GetDashboardByIdUseCase getDashboardByIdUseCase;
    private final DeleteDashboardByIdUseCase deleteDashboardByIdUseCase;

    public DashboardResource(CreateDashboardUseCase createDashboardUseCase, GetUserDashboardsUseCase getUserDashboardsUseCase, GetDashboardByIdUseCase getDashboardByIdUseCase, DeleteDashboardByIdUseCase deleteDashboardByIdUseCase) {
        this.createDashboardUseCase = createDashboardUseCase;
        this.getUserDashboardsUseCase = getUserDashboardsUseCase;
        this.getDashboardByIdUseCase = getDashboardByIdUseCase;
        this.deleteDashboardByIdUseCase = deleteDashboardByIdUseCase;
    }

    @POST
    public Response createDashboard(@Valid CreateDashboardDTO createDashboardDTO){
        return Response.status(Response.Status.CREATED).entity(createDashboardUseCase.execute(createDashboardDTO)).build();
    }

    @GET
    public Response getUserDashboards(){
        return Response.ok(getUserDashboardsUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    public Response getDashboardById(@PathParam("id") UUID id){
        try {
            return Response.ok(getDashboardByIdUseCase.execute(id)).build();
        } catch (DashboardNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response deleteDashboardById(@PathParam("id") UUID id){
        try {
            deleteDashboardByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch (DashboardNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
