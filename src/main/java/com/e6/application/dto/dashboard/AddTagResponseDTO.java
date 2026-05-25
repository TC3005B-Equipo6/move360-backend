package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.Tag;

public record AddTagResponseDTO(String title, String description, String tagName) {
    public static AddTagResponseDTO from(Dashboard dashboard, Tag tag) {
        return new AddTagResponseDTO(
                dashboard.getTitle(),
                dashboard.getDescription(),
                tag.getName()
        );
    }
}
