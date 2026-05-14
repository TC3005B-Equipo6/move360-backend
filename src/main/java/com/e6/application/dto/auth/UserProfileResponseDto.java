package com.e6.application.dto.auth;

import com.e6.domain.model.User;

public record UserProfileResponseDto(String firstName, String surname, String role, String email) {

    public static UserProfileResponseDto from(User user) {
        return new UserProfileResponseDto(
                user.getFirstName(),
                user.getPaternalSurname(),
                user.getRole().getName(),
                user.getEmail()
        );
    }
}
