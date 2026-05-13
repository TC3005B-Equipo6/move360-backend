package com.e6.infrastructure.security;

import com.e6.domain.model.User;
import com.e6.domain.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import io.quarkus.arc.profile.UnlessBuildProfile;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UnlessBuildProfile("test")
public class FirebaseAuthFilter implements ContainerRequestFilter {

    @Inject
    UserRepository userRepository;

    @Inject
    AuthContext authContext;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path=requestContext.getUriInfo().getPath();
        System.out.println(path);
        if(path.equals("/user")){
            return;
        }

        String authHeader = requestContext.getHeaders().getFirst("Authorization");
        if(authHeader==null){
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("No autorizado").build()
            );
            return;
        }
        if(!authHeader.startsWith("Bearer ")){
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("No autorizado").build()
            );
            return;
        }
        String token = authHeader.substring("Bearer ".length());
        try {
            FirebaseToken decodedToken= FirebaseAuth
                    .getInstance()
                    .verifyIdToken(token,true);
            Optional<User> userOptional= userRepository.findByFirebaseUuid(decodedToken.getUid());
            if(userOptional.isEmpty()){
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("No autorizado").build()
                );
            }
            User user = userOptional.get();
            authContext.setUser(user);

        } catch (FirebaseAuthException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("No autorizado").build()
            );

        }
    }
}