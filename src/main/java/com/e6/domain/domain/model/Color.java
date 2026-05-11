package com.e6.domain.domain.model;

import java.util.UUID;

public class Color {
    private UUID id;
    private String name;
    private String hex;

    public Color () {}

    public Color (UUID id, String name, String hex) {
        this.id = id;
        this.name = name;
        this.hex = hex;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
