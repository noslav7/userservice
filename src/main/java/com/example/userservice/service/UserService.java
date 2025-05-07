package com.example.userservice.service;

import com.example.userservice.dto.request.CreateUserDto;
import com.example.userservice.dto.request.UpdateUserDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserResponseDto create(CreateUserDto dto) {
        User u = mapper.toEntity(dto);
        log.debug("Создаём пользователя {}", u.getEmail());
        User saved = userRepo.save(u);
        return mapper.toDto(saved);
    }

    public UserResponseDto getById(Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        return mapper.toDto(u);
    }

    public UserResponseDto update(Long id, UpdateUserDto dto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        User toSave = mapper.toEntity(dto, existing);
        User saved = userRepo.save(toSave);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
        userRepo.delete(u);
    }

    public User getInternalEntity(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
    }
}
