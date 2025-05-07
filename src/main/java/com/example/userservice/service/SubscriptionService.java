package com.example.userservice.service;

import com.example.userservice.dto.request.SubscriptionRequestDto;
import com.example.userservice.dto.response.SubscriptionResponseDto;
import com.example.userservice.dto.response.TopSubscriptionDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.SubscriptionMapper;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import com.example.userservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subRepo;
    private final UserService userService;
    private final SubscriptionMapper mapper;

    public SubscriptionResponseDto add(Long userId, SubscriptionRequestDto dto) {
        User user = userService.getInternalEntity(userId);
        Subscription entity = mapper.toEntity(dto, user);
        Subscription saved = subRepo.save(entity);
        return mapper.toDto(saved);
    }

    public List<SubscriptionResponseDto> list(Long userId) {
        User user = userService.getInternalEntity(userId);
        return user.getSubscriptions().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long userId, Long subId) {
        Subscription s = subRepo.findById(subId)
                .filter(x -> x.getUser().getId().equals(userId))
                .orElseThrow(() -> new NotFoundException("Subscription", subId));
        subRepo.delete(s);
    }

    public List<TopSubscriptionDto> top3() {
        return subRepo
                .findTopSubscriptions(PageRequest.of(0, 3))
                .stream()
                .map(mapper::toTopDto)
                .collect(Collectors.toList());
    }
}
