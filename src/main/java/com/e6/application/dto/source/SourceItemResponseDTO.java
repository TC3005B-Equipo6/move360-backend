package com.e6.application.dto.source;

public record SourceItemResponseDTO(
        int id,
        String name
) {
    public static SourceItemResponseDTO of(int id, String name) {
        return new SourceItemResponseDTO(id, name);
    }
}
