package com.online.store.Services.Impls;


import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity saveUser(UserEntityDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntityRepository.save(dtoToEntity(user));
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userEntityRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public UserEntity dtoToEntity(UserEntityDto dto) {
        if(dto.getRoles() == null) {
            dto.setRoles("CUSTOMER");
        }
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(dto.getRoles())
                .build();
    }


}
