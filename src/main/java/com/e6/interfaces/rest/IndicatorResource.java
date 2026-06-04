package com.e6.interfaces.rest;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.GetIndicatorResponseDTO;
import com.e6.application.dto.indicator.UpdateIndicatorDTO;
import com.e6.application.usecase.indicator.CreateIndicatorUseCase;
import com.e6.application.usecase.indicator.DeleteIndicatorByIdUseCase;
import com.e6.application.usecase.indicator.GetIndicatorByIdUseCase;
import com.e6.application.usecase.indicator.UpdateIndicatorUseCase;
import com.e6.domain.exception.IndicatorNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/indicator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IndicatorResource {

    private final CreateIndicatorUseCase createIndicatorUseCase;
    private final GetIndicatorByIdUseCase getIndicatorByIdUseCase;
    private final UpdateIndicatorUseCase updateIndicatorUseCase;
    private final DeleteIndicatorByIdUseCase deleteIndicatorByIdUseCase;

    public IndicatorResource(CreateIndicatorUseCase createIndicatorUseCase, GetIndicatorByIdUseCase getIndicatorhByIdUseCase, UpdateIndicatorUseCase updateIndicatorUseCase, DeleteIndicatorByIdUseCase deletIndicatorByIdUseCase, DeleteIndicatorByIdUseCase deleteIndicatorByIdUseCase) {
        this.createIndicatorUseCase = createIndicatorUseCase;
        this.getIndicatorByIdUseCase = getIndicatorhByIdUseCase;
        this.updateIndicatorUseCase = updateIndicatorUseCase;
        this.deleteIndicatorByIdUseCase = deletIndicatorByIdUseCase;
    }

    @POST
    public Response createIndicator(CreateIndicatorDTO createIndicatorDTO) {
        return Response.status(Response.Status.CREATED).entity(createIndicatorUseCase.execute(createIndicatorDTO)).build();
    }

    @GET
    @Path("/{id}")
    public Response getIndicator(@PathParam("id") int id) {
        try {
            return Response.ok().entity(GetIndicatorResponseDTO.from(getIndicatorByIdUseCase.execute(id))).build();
        } catch (IndicatorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response updateIndicator(@PathParam("id") int id, UpdateIndicatorDTO updateIndicatorDTO) {
        try {
            return Response.ok().entity(updateIndicatorUseCase.execute(id, updateIndicatorDTO)).build();
        } catch (IndicatorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteIndicator(@PathParam("id") int id){
        try {
            deleteIndicatorByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch ( IndicatorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
