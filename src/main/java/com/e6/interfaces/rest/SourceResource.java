package com.e6.interfaces.rest;

import com.e6.application.usecase.source.GetColumnsUseCase;
import com.e6.application.usecase.source.GetFiltersUseCase;
import com.e6.application.security.PermitPublic;
import com.e6.application.usecase.source.GetSourcesUseCase;
import com.e6.application.usecase.source.GetTablesUseCase;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Read-only static catalog (sources/tables/columns/filters) used by the indicator
// builder. Public like /graph/catalog — it exposes no user data, so it must not
// require auth (a stale Firebase token would otherwise 401 and empty the modal).
@Path("/source")
@PermitPublic
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SourceResource {

    private final GetSourcesUseCase getSourcesUseCase;
    private final GetTablesUseCase getTablesUseCase;
    private final GetColumnsUseCase getColumnsUseCase;
    private final GetFiltersUseCase getFiltersUseCase;

    public SourceResource(GetSourcesUseCase getSourcesUseCase, GetTablesUseCase getTablesUseCase, GetColumnsUseCase getColumnsUseCase, GetFiltersUseCase getFiltersUseCase) {
        this.getSourcesUseCase = getSourcesUseCase;
        this.getTablesUseCase = getTablesUseCase;
        this.getColumnsUseCase = getColumnsUseCase;
        this.getFiltersUseCase = getFiltersUseCase;
    }

    @GET
    public Response getSources(){
        return Response.ok(getSourcesUseCase.execute()).build();
    }

    @GET
    @Path("/{id}")
    public Response getTables(@PathParam("id") int id){
        return Response.ok(getTablesUseCase.execute(id)).build();
    }

    @GET
    @Path("/column/{id}")
    public Response getColumns(@PathParam("id") int id){
        return Response.ok(getColumnsUseCase.execute(id)).build();
    }

    @GET
    @Path("/filter/{id}")
    public Response getFilters(@PathParam("id") int id){
        return Response.ok(getFiltersUseCase.execute(id)).build();
    }
}
