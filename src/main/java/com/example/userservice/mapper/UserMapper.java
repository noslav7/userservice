package com.example.userservice.mapper;

import com.example.userservice.dto.request.CreateUserDto;
import com.example.userservice.dto.request.UpdateUserDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserDto dto) {
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        return u;
    }

    public User toEntity(UpdateUserDto dto, User existing) {
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        return existing;
    }

    public UserResponseDto toDto(User u) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        return dto;
    }
}
