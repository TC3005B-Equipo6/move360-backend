package com.e6.interfaces.rest;

import com.e6.application.security.PermitPublic;
import com.e6.domain.model.Indicator.Operation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@PermitPublic
public class StatusResource {

    @GET
    public Response status(){
        return Response.ok(Operation.AVERAGE.toString()).build();
    }

}
