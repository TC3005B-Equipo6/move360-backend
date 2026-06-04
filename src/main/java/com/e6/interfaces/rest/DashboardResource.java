package com.e6.interfaces.rest;

import com.e6.application.dto.dashboard.CreateDashboardDTO;
import com.e6.application.dto.dashboard.UpdateDashboardDTO;
import com.e6.application.usecase.dashboard.*;
import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    private final CreateDashboardUseCase createDashboardUseCase;
    private final GetPublicDashboardsUseCase getPublicDashboardsUseCase;
    private final GetUserDashboardsUseCase getUserDashboardsUseCase;
    private final GetDashboardByIdUseCase getDashboardByIdUseCase;
    private final UpdateDashboardUseCase updateDashboardUseCase;
    private final DeleteDashboardByIdUseCase deleteDashboardByIdUseCase;
    private final AddTagUseCase addTagUseCase;
    private final RemoveTagUseCase removeTagUseCase;

    public DashboardResource(CreateDashboardUseCase createDashboardUseCase, GetPublicDashboardsUseCase getPublicDashboardsUseCase, GetUserDashboardsUseCase getUserDashboardsUseCase, GetDashboardByIdUseCase getDashboardByIdUseCase, UpdateDashboardUseCase updateDashboardUseCase, DeleteDashboardByIdUseCase deleteDashboardByIdUseCase, AddTagUseCase addTagUseCase, RemoveTagUseCase removeTagUseCase) {
        this.createDashboardUseCase = createDashboardUseCase;
        this.getPublicDashboardsUseCase = getPublicDashboardsUseCase;
        this.getUserDashboardsUseCase = getUserDashboardsUseCase;
        this.getDashboardByIdUseCase = getDashboardByIdUseCase;
        this.deleteDashboardByIdUseCase = deleteDashboardByIdUseCase;
        this.updateDashboardUseCase = updateDashboardUseCase;
        this.addTagUseCase = addTagUseCase;
        this.removeTagUseCase = removeTagUseCase;
    }

    @POST
    public Response createDashboard(@Valid CreateDashboardDTO createDashboardDTO) {
        return Response.status(Response.Status.CREATED).entity(createDashboardUseCase.execute(createDashboardDTO)).build();
    }

    @GET
    public Response getPublicDashboards() {
        return Response.ok(getPublicDashboardsUseCase.execute()).build();
    }

    @GET
    @Path("/me")
    public Response getUserDashboards() {
        return Response.ok(getUserDashboardsUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    public Response getDashboard(@PathParam("id") UUID id) {
        try {
            return Response.ok(getDashboardByIdUseCase.execute(id)).build();
        } catch (DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response updateDashboard(@PathParam("id") UUID id, UpdateDashboardDTO updateDashboardDTO){
        return Response.ok(updateDashboardUseCase.execute(id, updateDashboardDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDashboard(@PathParam("id") UUID id) {
        try {
            deleteDashboardByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch (DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{id}/tag/{tagId}")
    public Response addTag(@PathParam("id") UUID id, @PathParam("tagId") int tagId) {
        try {
            return Response.status(Response.Status.CREATED).entity(addTagUseCase.execute(id, tagId)).build();
        } catch (TagNotFoundException | DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}/tag/{tagId}")
    public Response removeTag(@PathParam("id") UUID id, @PathParam("tagId") int tagId) {
        try {
            removeTagUseCase.execute(id, tagId);
            return Response.noContent().build();
        } catch (TagNotFoundException | DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
