package com.e6.infrastructure.repository;

import com.e6.domain.model.User;
import com.e6.domain.repository.UserRepository;
import com.e6.infrastructure.entity.UserEntity;
import com.e6.infrastructure.mapper.UserMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryImpl implements
        UserRepository,
        PanacheRepositoryBase<UserEntity, UUID> {

    @Override
    public Optional<User> findDomainById(UUID id) {

        UserEntity entity = findById(id);

        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(UserMapper.toDomain(entity));
    }

    @Override
    public Optional<User> findByFirebaseUuid(String firebaseUuid) {

        return find("firebaseUuid", firebaseUuid)
                .firstResultOptional()
                .map(UserMapper::toDomain);
    }
}