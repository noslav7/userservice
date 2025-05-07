package com.example.userservice.mapper;

import com.example.userservice.dto.request.SubscriptionRequestDto;
import com.example.userservice.dto.response.SubscriptionResponseDto;
import com.example.userservice.dto.response.TopSubscriptionDto;
import com.example.userservice.model.*;
import com.example.userservice.repository.TopSubscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public Subscription toEntity(SubscriptionRequestDto dto, User user) {
        Subscription s = new Subscription();
        s.setServiceName(dto.getServiceName());
        s.setUser(user);
        return s;
    }

    public SubscriptionResponseDto toDto(Subscription s) {
        SubscriptionResponseDto dto = new SubscriptionResponseDto();
        dto.setId(s.getId());
        dto.setServiceName(s.getServiceName());
        return dto;
    }

    public TopSubscriptionDto toTopDto(TopSubscription proj) {
        return new TopSubscriptionDto(proj.getName(), proj.getCount());
    }
}
