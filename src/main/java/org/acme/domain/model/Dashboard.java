package org.acme.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Dashboard {
    private UUID id;
    private User owner;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private Set<Tag> tags = new HashSet<>();

    public Dashboard() {}

    public Dashboard(UUID id, User owner, String title, String description, LocalDateTime createdAt, boolean isPublic, Set<Tag> tags) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
        this.tags = tags;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
