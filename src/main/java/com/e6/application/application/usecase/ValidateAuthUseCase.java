package com.e6.application.application.usecase;

import com.e6.domain.domain.model.User;
import com.e6.domain.domain.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@ApplicationScoped
public class ValidateAuthUseCase {

    private final UserRepository userRepository;

    public ValidateAuthUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response execute(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(401).entity("No token").build();
            }

            String token = authHeader.substring(7).trim();

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String firebaseUid = decodedToken.getUid();

            Optional<User> userOptional = userRepository.findByFirebaseUuid(firebaseUid);

            if (userOptional.isEmpty()) {
                return Response.status(404)
                        .entity("El token es válido, pero el usuario no existe en app_user")
                        .build();
            }

            User user = userOptional.get();

            if (!user.isActive()) {
                return Response.status(403)
                        .entity("Usuario desactivado")
                        .build();
            }

            return Response.ok(user).build();

        } catch (Exception e) {
            return Response.status(401)
                    .entity("Token inválido: " + e.getMessage())
                    .build();
        }
    }
}