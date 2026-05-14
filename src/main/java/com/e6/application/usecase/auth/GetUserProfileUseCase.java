package com.e6.application.usecase.auth;

import com.e6.application.dto.auth.UserProfileResponseDto;
import com.e6.domain.model.User;
import com.e6.infrastructure.security.AuthContext;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetUserProfileUseCase {

    private final AuthContext authContext;

    public GetUserProfileUseCase(AuthContext authContext) {
        this.authContext = authContext;
    }

    public UserProfileResponseDto execute() {
        User user = authContext.getUser();

        if(user == null)
            throw new UnauthorizedException();

        return UserProfileResponseDto.from(user);
    }
}