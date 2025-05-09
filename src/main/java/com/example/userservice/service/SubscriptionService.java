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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subRepo;
    private final UserService userService;
    private final SubscriptionMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionResponseDto add(Long userId, SubscriptionRequestDto dto) {
        log.debug("Добавляем подписку '{}' для пользователя {}", dto.getServiceName(), userId);
        User user = userService.getInternalEntity(userId);
        Subscription entity = mapper.toEntity(dto, user);
        Subscription saved = subRepo.save(entity);
        log.info("Подписка '{}' создана с id={} для пользователя {}", saved.getServiceName(), saved.getId(), userId);
        return mapper.toDto(saved);
    }

    public List<SubscriptionResponseDto> list(Long userId) {
        log.debug("Запрашиваем список подписок для пользователя {}", userId);
        User user = userService.getInternalEntity(userId);
        return user.getSubscriptions().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long userId, Long subId) {
        log.debug("Удаляем подписку id={} у пользователя {}", subId, userId);
        Subscription s = subRepo.findById(subId)
                .filter(x -> x.getUser().getId().equals(userId))
                .orElseThrow(() -> new NotFoundException("Subscription", subId));
        subRepo.delete(s);
        log.info("Подписка id={} удалена у пользователя {}", subId, userId);
    }

    public List<TopSubscriptionDto> top3() {
        log.debug("Запрашиваем ТОП-3 популярных подписок");
        List<TopSubscriptionDto> top = subRepo.findTopSubscriptions(PageRequest.of(0, 3)).stream()
                .map(mapper::toTopDto)
                .collect(Collectors.toList());
        log.info("Получен ТОП-{} популярных подписок", top.size());
        return top;
    }
}