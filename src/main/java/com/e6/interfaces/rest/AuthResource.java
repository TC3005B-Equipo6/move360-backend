package com.e6.interfaces.rest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @GET
    @Path("/validate")
    public Response validar(@HeaderParam("Authorization") String authHeader) {
        try {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(401).entity("No token").build();
            }

           // String token = authHeader.replace("Bearer ", "");
            String token = authHeader.substring(7).trim();

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(token);

            return Response.ok("UID: " + decodedToken.getUid()).build();

       /* } catch (Exception e) {
            return Response.status(401).entity("Token inválido").build();
        }*/

        } catch (Exception e) {
            e.printStackTrace();
            
            return Response.status(401)
                .entity("Token inválido: " + e.getMessage())
                .build();
        }
    }
}