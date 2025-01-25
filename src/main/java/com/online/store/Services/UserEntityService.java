package com.online.store.Services;

import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserEntityService {
    UserEntity saveUser(UserEntityDto user);
    Optional<UserEntityDto> findById(Long id);
    Optional<UserEntityDto> findByUsername(String username);
    List<UserEntityDto> findAll();
    Optional<UserEntityDto> findByEmail(String email);
    UserEntity dtoToUserEntity(UserEntityDto dto);
    UserEntityDto userToDto(UserEntity user);
}
