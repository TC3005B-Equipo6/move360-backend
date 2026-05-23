package com.e6.infrastructure.mapper;

import com.e6.domain.model.Color;
import com.e6.infrastructure.entity.ColorEntity;

import java.util.List;

public final class ColorMapper {

    public static Color toDomain(ColorEntity colorEntity){
        return Color.builder()
                .id(colorEntity.getId())
                .name(colorEntity.getName())
                .hex(colorEntity.getHex())
                .build();
    }

    public static ColorEntity toEntity(Color color){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setName(color.getName());
        colorEntity.setHex(color.getHex());
        return colorEntity;
    }
}
