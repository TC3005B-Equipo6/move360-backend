package com.e6.application.dto.auth;

import com.e6.domain.model.User;

public record UserProfileResponseDTO(String firstName, String paternalSurname, String maternalSurname, String role, String email) {

    public static UserProfileResponseDTO from(User user) {
        return new UserProfileResponseDTO(
                user.getFirstName(),
                user.getPaternalSurname(),
                user.getMaternalSurname(),
                user.getRole().getName(),
                user.getEmail()
        );
    }
}
