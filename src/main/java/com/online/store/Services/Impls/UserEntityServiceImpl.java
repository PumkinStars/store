package com.online.store.Services.Impls;


import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return userEntityRepository.save(dtoToUserEntity(user));
    }

    @Override
    public Optional<UserEntityDto> findById(Long id) {
        Optional<UserEntity> user = userEntityRepository.findById(id);
        return user.map(this::userToDto);
    }

    @Override
    public Optional<UserEntityDto> findByUsername(String username) {
        Optional<UserEntity> user = userEntityRepository.findByUsername(username);
        return user.map(this::userToDto);
    }

    @Override
    public List<UserEntityDto> findAll() {
        List<UserEntity> allUsers = userEntityRepository.findAll();
        return allUsers.stream().map(this::userToDto).toList();
    }

    @Override
    public Optional<UserEntityDto> findByEmail(String email) {
        Optional<UserEntity> user = userEntityRepository.findByEmail(email);
        return user.map(this::userToDto);
    }

    @Override
    public UserEntity dtoToUserEntity(UserEntityDto dto) {
        if(dto.getRoles() == null) {
            dto.setRoles("CUSTOMER");
        }
        return UserEntity.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(dto.getRoles())
                .build();
    }

    @Override
    public UserEntityDto userToDto(UserEntity user) {
        return UserEntityDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }


}
