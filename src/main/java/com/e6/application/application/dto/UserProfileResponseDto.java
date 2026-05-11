package com.e6.application.application.dto;


public class UserProfileResponseDto {

    private String name;
    private String role;

    public UserProfileResponseDto(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
