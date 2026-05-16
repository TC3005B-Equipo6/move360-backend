package com.e6.infrastructure.mapper;

import com.e6.domain.model.User;
import com.e6.infrastructure.entity.UserEntity;

public final class UserMapper {

    public static User toDomain(UserEntity entity){
        User user= new User();
        user.setId(entity.getId());
        user.setActive(entity.isActive());
        user.setEmail(entity.getEmail());
        user.setFirebaseUuid(entity.getFirebaseUuid());
        user.setRole(RoleMapper.toDomain(entity.getRole()));
        user.setFirstName(entity.getFirstName());
        user.setPaternalSurname(entity.getPaternalSurname());
        user.setMaternalSurname(entity.getMaternalSurname());
        return user;
    }

    public static UserEntity toEntity(User user){
        UserEntity entity= new UserEntity();
        entity.setId(user.getId());
        entity.setActive(user.isActive());
        entity.setEmail(user.getEmail());
        entity.setFirebaseUuid(user.getFirebaseUuid());
        entity.setRole(RoleMapper.toEntity(user.getRole()));
        entity.setFirstName(user.getFirstName());
        entity.setPaternalSurname(user.getPaternalSurname());
        entity.setMaternalSurname(user.getMaternalSurname());
        return entity;
    }
}