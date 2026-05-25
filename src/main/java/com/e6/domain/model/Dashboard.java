package com.e6.domain.model;

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

    public Dashboard() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private User owner;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private boolean isPublic;
        private Set<Tag> tags = new HashSet<>();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder isPublic(boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Dashboard build() {
            Dashboard dashboard = new Dashboard();
            dashboard.id = this.id;
            dashboard.owner = this.owner;
            dashboard.title = this.title;
            dashboard.description = this.description;
            dashboard.createdAt = this.createdAt;
            dashboard.isPublic = this.isPublic;
            dashboard.tags = this.tags;
            return dashboard;
        }
    }

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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
