package com.e6.infrastructure.mapper;

import com.e6.domain.model.Color;
import com.e6.infrastructure.entity.ColorEntity;

import java.util.List;

public final class ColorMapper {

    public static Color toDomain(ColorEntity colorEntity){
        Color color = new Color();
        color.setId(colorEntity.getId());
        color.setName(colorEntity.getName());
        color.setHex(colorEntity.getHex());
        return color;
    }

    public static ColorEntity toEntity(Color color){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(color.getId());
        colorEntity.setName(color.getName());
        colorEntity.setHex(color.getHex());
        return colorEntity;
    }
}
