package com.example.userservice.controller;

import com.example.userservice.dto.request.CreateUserDto;
import com.example.userservice.dto.request.UpdateUserDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid CreateUserDto dto) {
        log.info("POST /users — создание пользователя: name='{}', email='{}'", dto.getName(), dto.getEmail());
        UserResponseDto created = userService.create(dto);
        log.debug("Пользователь создан с id={}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        log.info("GET /users/{} — получение пользователя", id);
        UserResponseDto user = userService.getById(id);
        log.debug("Найден пользователь: {}", user);
        return user;
    }

    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDto dto
    ) {
        log.info("PUT /users/{} — обновление пользователя: name='{}', email='{}'", id, dto.getName(), dto.getEmail());
        UserResponseDto updated = userService.update(id, dto);
        log.debug("Пользователь {} успешно обновлён", id);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("DELETE /users/{} — удаление пользователя", id);
        userService.delete(id);
        log.debug("Пользователь {} удалён", id);
    }
}
