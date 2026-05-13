package com.e6.application.dto.auth;


import com.e6.domain.model.User;

public class UserProfileResponseDto {

    private final String firstName;
    private final String surname;
    private final String role;
    private final String email;

    public UserProfileResponseDto(String name, String surname, String role, String email) {
        this.firstName = name;
        this.surname = surname;
        this.role = role;
        this.email = email;
    }

    public static UserProfileResponseDto from(User user) {
        return new UserProfileResponseDto(
                user.getFirstName(),
                user.getPaternalSurname(),
                user.getRole().getName(),
                user.getEmail()
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname(){ return surname; }

    public String getRole() {
        return role;
    }

    public String getEmail(){ return email; }
}
