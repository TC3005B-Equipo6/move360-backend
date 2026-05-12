package com.e6.application.dto;


public class UserProfileResponseDto {

    private String firstName;
    private String surname;
    private String role;

    public UserProfileResponseDto(String name, String surname, String role) {
        this.firstName = name;
        this.surname = surname;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname(){ return surname; }

    public String getRole() {
        return role;
    }
}
