package com.example.userservice.controller;

import com.example.userservice.dto.request.SubscriptionRequestDto;
import com.example.userservice.dto.response.SubscriptionResponseDto;
import com.example.userservice.dto.response.TopSubscriptionDto;
import com.example.userservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
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
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionResponseDto> add(
            @PathVariable("id") Long userId,
            @RequestBody @Valid SubscriptionRequestDto dto
    ) {
        SubscriptionResponseDto created = subscriptionService.add(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/users/{id}/subscriptions")
    public List<SubscriptionResponseDto> list(@PathVariable("id") Long userId) {
        return subscriptionService.list(userId);
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Long userId,
            @PathVariable("subId") Long subscriptionId
    ) {
        subscriptionService.delete(userId, subscriptionId);
    }

    @GetMapping("/subscriptions/top")
    public List<TopSubscriptionDto> top3() {
        return subscriptionService.top3();
    }
}
