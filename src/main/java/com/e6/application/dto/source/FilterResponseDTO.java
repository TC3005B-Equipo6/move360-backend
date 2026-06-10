package com.e6.application.dto.source;

public record FilterResponseDTO(
        int id,
        String name,
        String[] values
) {
    public static FilterResponseDTO of(int id, String name, String[] values) {
        return new FilterResponseDTO(id, name, values);
    }
}
