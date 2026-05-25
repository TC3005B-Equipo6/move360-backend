package com.e6.interfaces.rest;

import com.e6.application.dto.tag.CreateTagDTO;
import com.e6.application.dto.tag.UpdateTagDTO;
import com.e6.application.usecase.dashboard.RemoveTagUseCase;
import com.e6.application.usecase.tag.*;
import com.e6.domain.exception.ColorNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("tag")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    private final CreateTagUseCase createTagUseCase;
    private final GetDashboardTagsUseCase getDashboardTagsUseCase;
    private final GetTagsUseCase getTagsUseCase;
    private final UpdateTagUseCase updateTagUseCase;
    private final RemoveTagUseCase removeTagUseCase;
    private final DeleteTagByIdUseCase deleteTagByIdUseCase;

    public TagResource(CreateTagUseCase createTagUseCase, GetDashboardTagsUseCase getDashboardTagsUseCase, GetTagsUseCase getTagsUseCase, UpdateTagUseCase updateTagUseCase, RemoveTagUseCase removeTagUseCase, DeleteTagByIdUseCase deleteTagByIdUseCase) {
        this.createTagUseCase = createTagUseCase;
        this.getDashboardTagsUseCase = getDashboardTagsUseCase;
        this.getTagsUseCase = getTagsUseCase;
        this.updateTagUseCase = updateTagUseCase;
        this.removeTagUseCase = removeTagUseCase;
        this.deleteTagByIdUseCase = deleteTagByIdUseCase;
    }

    @POST
    public Response createTag(@Valid CreateTagDTO createTagDTO) {
        try {
            return Response.status(Response.Status.CREATED).entity(createTagUseCase.execute(createTagDTO)).build();
        } catch (ColorNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response getTags(){
        return Response.ok(getTagsUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    public Response getDashboardTags(@PathParam("id") UUID id){
        return Response.ok(getDashboardTagsUseCase.execute(id)).build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateDashboard(@PathParam("id") int id, @Valid UpdateTagDTO updateTagDTO){
        return Response.ok(updateTagUseCase.execute(id, updateTagDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTag(@PathParam("id") int id){
        try{
            deleteTagByIdUseCase.execute(id);
            return Response.noContent().build();
        }catch (TagNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
