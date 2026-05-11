package com.e6.domain.repository;

import com.e6.domain.model.User;

import java.util.Optional;
import java.util.UUID;
 


public interface UserRepository {

    Optional<User> findDomainById(UUID id);

    Optional<User> findByFirebaseUuid(String firebaseUuid);
}