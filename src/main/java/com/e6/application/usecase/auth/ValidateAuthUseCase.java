package com.e6.application.usecase.auth;

import com.e6.domain.model.User;
import com.e6.infrastructure.security.AuthContext;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ValidateAuthUseCase {

    private final AuthContext authContext;

    public ValidateAuthUseCase(AuthContext authContext) {
        this.authContext = authContext;
    }

    public User execute() {
        User user = authContext.getUser();

        if( user == null){
            throw new UnauthorizedException();
        }

        return user;
    }
}