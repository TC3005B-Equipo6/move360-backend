package org.acme.domain.model;

import java.util.UUID;

public class Tag {
    private UUID id;
    private String name;
    private Color color;

    public Tag () {}

    public Tag (UUID id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
