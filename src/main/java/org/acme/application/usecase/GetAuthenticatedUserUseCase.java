package org.acme.application.usecase;

import org.acme.domain.model.User;
import org.acme.domain.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class GetAuthenticatedUserUseCase {

    private final UserRepository userRepository;

    public GetAuthenticatedUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new WebApplicationException("No token", Response.Status.UNAUTHORIZED);
            }

            String token = authHeader.substring(7).trim();

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String firebaseUid = decodedToken.getUid();

            User user = userRepository.findByFirebaseUuid(firebaseUid)
                    .orElseThrow(() -> new WebApplicationException(
                            "Usuario no encontrado en la base de datos",
                            Response.Status.NOT_FOUND
                    ));

            if (!user.isActive()) {
                throw new WebApplicationException(
                        "Usuario desactivado",
                        Response.Status.FORBIDDEN
                );
            }

            return user;

        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(
                    "Token inválido: " + e.getMessage(),
                    Response.Status.UNAUTHORIZED
            );
        }
    }
}