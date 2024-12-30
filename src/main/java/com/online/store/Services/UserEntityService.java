package com.online.store.Services;

import com.online.store.Models.UserEntity;
import com.online.store.Models.Dtos.UserEntityDto;

import java.util.Optional;

public interface UserEntityService {
    UserEntity saveUser(UserEntityDto user);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    UserEntity dtoToEntity(UserEntityDto dto);

}
