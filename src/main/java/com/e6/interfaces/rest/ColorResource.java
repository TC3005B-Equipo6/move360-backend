package com.e6.interfaces.rest;

import com.e6.application.dto.color.CreateColorDTO;
import com.e6.application.dto.color.UpdateColorDTO;
import com.e6.application.usecase.color.*;
import com.e6.domain.exception.ColorNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/color")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColorResource {

    private final CreateColorUseCase createColorUseCase;
    private final GetColorsUseCase getColorsUseCase;
    private final GetColorByIdUseCase getColorByIdUseCase;
    private final UpdateColorUseCase updateColorUseCase;
    private final DeleteColorByIdUseCase deleteColorByIdUseCase;

    public ColorResource(CreateColorUseCase createColorUseCase, GetColorsUseCase getColorsUseCase, GetColorByIdUseCase getColorByIdUseCase, UpdateColorUseCase updateColorUseCase, DeleteColorByIdUseCase deleteColorByIdUseCase) {
        this.createColorUseCase = createColorUseCase;
        this.getColorsUseCase = getColorsUseCase;
        this.getColorByIdUseCase = getColorByIdUseCase;
        this.updateColorUseCase = updateColorUseCase;
        this.deleteColorByIdUseCase = deleteColorByIdUseCase;
    }

    @POST
    public Response createColor(@Valid CreateColorDTO createColorDTO){
        return Response.status(Response.Status.CREATED).entity(createColorUseCase.execute(createColorDTO)).build();
    }

    @GET
    public Response getColors(){
        return Response.ok(getColorsUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    public Response getColor(@PathParam("id") int id){
        try{
            return Response.ok(getColorByIdUseCase.execute(id)).build();
        } catch( ColorNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

    @PATCH
    @Path("{id}")
    public Response updateColor(@PathParam("id") int id, @Valid UpdateColorDTO updateColorDTO){
        try{
            return Response.ok(updateColorUseCase.execute(id, updateColorDTO)).build();
        } catch(ColorNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteColor(@PathParam("id") int id){
        try{
            deleteColorByIdUseCase.execute(id);
            return Response.noContent().build();
        } catch(ColorNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
