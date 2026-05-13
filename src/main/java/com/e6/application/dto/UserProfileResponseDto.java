package com.e6.application.dto;


import com.e6.domain.model.User;

public class UserProfileResponseDto {

    private String firstName;
    private String surname;
    private String role;
    private String email;

    public UserProfileResponseDto(){}

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
