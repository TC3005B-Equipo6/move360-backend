package com.e6.infrastructure.mapper;
import com.e6.domain.model.Role;
import com.e6.domain.model.User;
import com.e6.infrastructure.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setEmail(entity.getEmail());
        user.setActive(entity.isActive());
        user.setFirebaseUuid(entity.getFirebaseUuid());

      if (entity.getRole() != null) {
    Role role = new Role();
    role.setId(entity.getRole().getId());
    role.setName(entity.getRole().getName());
    user.setRole(role);
}
        return user;
    }
}