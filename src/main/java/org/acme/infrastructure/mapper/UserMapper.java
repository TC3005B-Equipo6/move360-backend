package org.acme.infrastructure.mapper;
import org.acme.domain.model.Role;
import org.acme.domain.model.User;
import org.acme.infrastructure.entity.UserEntity;


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