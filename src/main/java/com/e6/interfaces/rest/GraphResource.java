package com.e6.interfaces.rest;

import com.e6.application.dto.graph.CreateGraphDTO;
import com.e6.application.dto.graph.UpdateGraphDTO;
import com.e6.application.usecase.graph.CreateGraphUseCase;
import com.e6.application.usecase.graph.DeleteGraphByIdUseCase;
import com.e6.application.usecase.graph.GetGraphCatalogUseCase;
import com.e6.application.usecase.graph.GetGraphByIdUseCase;
import com.e6.application.usecase.graph.UpdateGraphUseCase;
import com.e6.application.security.PermitPublic;
import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.GraphNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GraphResource {

    private final CreateGraphUseCase createGraphUseCase;
    private final GetGraphCatalogUseCase getGraphCatalogUseCase;
    private final GetGraphByIdUseCase getGraphByIdUseCase;
    private final UpdateGraphUseCase updateGraphUseCase;
    private final DeleteGraphByIdUseCase deleteGraphByIdUseCase;

    public GraphResource(
            CreateGraphUseCase createGraphUseCase,
            GetGraphCatalogUseCase getGraphCatalogUseCase,
            GetGraphByIdUseCase getGraphByIdUseCase,
            UpdateGraphUseCase updateGraphUseCase,
            DeleteGraphByIdUseCase deleteGraphByIdUseCase) {
        this.createGraphUseCase = createGraphUseCase;
        this.getGraphCatalogUseCase = getGraphCatalogUseCase;
        this.getGraphByIdUseCase = getGraphByIdUseCase;
        this.updateGraphUseCase = updateGraphUseCase;
        this.deleteGraphByIdUseCase = deleteGraphByIdUseCase;
    }

    @POST
    public Response createGraph(@Valid CreateGraphDTO createGraphDTO) {
        try {
            return Response.status(Response.Status.CREATED).entity(createGraphUseCase.execute(createGraphDTO)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/catalog")
    @PermitPublic
    public Response getGraphCatalog() {
        return Response.ok(getGraphCatalogUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    @PermitPublic
    public Response getGraphById(@PathParam("id") int id) {
        try {
            return Response.ok().entity(getGraphByIdUseCase.execute(id)).build();
        } catch (GraphNotFoundException | DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response updateGraph(@PathParam("id") int id, UpdateGraphDTO updateGraphDTO) {
        try {
            return Response.ok().entity(updateGraphUseCase.execute(id, updateGraphDTO)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (GraphNotFoundException | DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGraph(@PathParam("id") int id) {
        try {
            deleteGraphByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch (GraphNotFoundException | DashboardNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
