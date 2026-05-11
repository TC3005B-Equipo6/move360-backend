package org.acme.application.dto;

public class UserInfoResponseDto {
    


    private String email;

    public UserInfoResponseDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}