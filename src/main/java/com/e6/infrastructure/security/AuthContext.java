package com.e6.infrastructure.security;

import com.e6.domain.model.User;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AuthContext {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}