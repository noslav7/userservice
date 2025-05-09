package com.example.userservice.controller;

import com.example.userservice.dto.request.SubscriptionRequestDto;
import com.example.userservice.dto.response.SubscriptionResponseDto;
import com.example.userservice.dto.response.TopSubscriptionDto;
import com.example.userservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class SubscriptionController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionResponseDto> add(
            @PathVariable("id") Long userId,
            @RequestBody @Valid SubscriptionRequestDto dto
    ) {
        log.info("POST /users/{}/subscriptions — добавление подписки '{}'", userId, dto.getServiceName());
        SubscriptionResponseDto created = subscriptionService.add(userId, dto);
        log.debug("Создана подписка id={} для пользователя {}", created.getId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/users/{id}/subscriptions")
    public List<SubscriptionResponseDto> list(@PathVariable("id") Long userId) {
        log.info("GET /users/{}/subscriptions — список подписок", userId);
        List<SubscriptionResponseDto> subs = subscriptionService.list(userId);
        log.debug("Найдено {} подписок для пользователя {}", subs.size(), userId);
        return subs;
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Long userId,
            @PathVariable("subId") Long subscriptionId
    ) {
        log.info("DELETE /users/{}/subscriptions/{} — удаление подписки", userId, subscriptionId);
        subscriptionService.delete(userId, subscriptionId);
        log.debug("Подписка {} удалена у пользователя {}", subscriptionId, userId);
    }

    @GetMapping("/subscriptions/top")
    public List<TopSubscriptionDto> top() {
        log.info("GET /subscriptions/top — топ-3 популярных подписок");
        List<TopSubscriptionDto> top = subscriptionService.top3();
        log.debug("Топ-3 подписок: {}", top);
        return top;
    }
}
