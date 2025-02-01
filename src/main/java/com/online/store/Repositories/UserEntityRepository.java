package com.online.store.Repositories;

import com.online.store.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername (String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> deleteByEmail(String email);
}
