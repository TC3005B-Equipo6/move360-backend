package com.e6.infrastructure.mapper;

import com.e6.domain.model.Role;
import com.e6.infrastructure.entity.RoleEntity;

public final class RoleMapper {

    public static Role toDomain(RoleEntity entity){
        Role role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        return role;
    }

    public static RoleEntity toEntity(Role role){
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        return entity;
    }
}
