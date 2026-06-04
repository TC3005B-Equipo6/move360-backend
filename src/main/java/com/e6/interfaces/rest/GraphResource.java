package com.e6.interfaces.rest;

import com.e6.application.dto.graph.CreateGraphDTO;
import com.e6.application.dto.graph.UpdateGraphDTO;
import com.e6.application.usecase.graph.CreateGraphUseCase;
import com.e6.application.usecase.graph.DeleteGraphByIdUseCase;
import com.e6.application.usecase.graph.GetGraphByIdUseCase;
import com.e6.application.usecase.graph.UpdateGraphUseCase;
import com.e6.application.usecase.indicator.DeleteIndicatorByIdUseCase;
import com.e6.domain.exception.GraphNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GraphResource {

    private final CreateGraphUseCase createGraphUseCase;
    private final GetGraphByIdUseCase getGraphByIdUseCase;
    private final UpdateGraphUseCase updateGraphUseCase;
    private final DeleteGraphByIdUseCase deleteGraphByIdUseCase;

    public GraphResource(CreateGraphUseCase createGraphUseCase, GetGraphByIdUseCase getGraphByIdUseCase, UpdateGraphUseCase updateGraphUseCase, DeleteGraphByIdUseCase deleteGraphByIdUseCase, DeleteIndicatorByIdUseCase deleteIndicatorByIdUseCase) {
        this.createGraphUseCase = createGraphUseCase;
        this.getGraphByIdUseCase = getGraphByIdUseCase;
        this.updateGraphUseCase = updateGraphUseCase;
        this.deleteGraphByIdUseCase = deleteGraphByIdUseCase;
    }

    @POST
    public Response createGraph(CreateGraphDTO createGraphDTO){
        return Response.status(Response.Status.CREATED).entity(createGraphUseCase.execute(createGraphDTO)).build();
    }

    @GET
    @Path("/{id}")
    public Response getGraphById(@PathParam("id") int id){
        try{
            return Response.ok().entity(getGraphByIdUseCase.execute(id)).build();
        } catch (GraphNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response updateGraph(@PathParam("id") int id, UpdateGraphDTO updateGraphDTO){
        try{
            return Response.ok().entity(updateGraphUseCase.execute(id, updateGraphDTO)).build();
        } catch (GraphNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGraph(@PathParam("id") int id){
        try{
            deleteGraphByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch (GraphNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
