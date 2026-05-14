package com.e6.infrastructure.security;

import com.e6.application.security.PermitPublic;
import com.e6.domain.model.User;
import com.e6.domain.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import io.quarkus.arc.profile.UnlessBuildProfile;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UnlessBuildProfile("test")
public class FirebaseAuthFilter implements ContainerRequestFilter {

    private final UserRepository userRepository;
    private final AuthContext authContext;
    private final ResourceInfo resourceInfo;

    public FirebaseAuthFilter(UserRepository userRepository, AuthContext authContext, ResourceInfo resourceInfo) {
        this.userRepository = userRepository;
        this.authContext = authContext;
        this.resourceInfo = resourceInfo;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String path=requestContext.getUriInfo().getPath();
        System.out.println(path);

        Method method = resourceInfo.getResourceMethod();
        Class<?> resourceClass = resourceInfo.getResourceClass();
        boolean isPublic = method.isAnnotationPresent(PermitPublic.class)
                        || resourceClass.isAnnotationPresent(PermitPublic.class);

        if (isPublic) {
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