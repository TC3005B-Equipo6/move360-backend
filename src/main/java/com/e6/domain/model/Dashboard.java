package com.e6.domain.model;

import java.util.Date;
import java.util.UUID;

public class Dashboard {
    private UUID id;
    private User owner;
    private String title;
    private String description;
    private Date createdAt;
    private boolean active;

    public Dashboard() {}

    public Dashboard(UUID id, User owner, String title, String description, Date createdAt, boolean active) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {}
}
