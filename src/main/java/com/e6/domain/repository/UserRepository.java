package com.e6.domain.repository;

import com.e6.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByFirebaseUuid(String firebaseUuid);
}