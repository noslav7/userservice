package com.example.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionRequestDto {
    @NotBlank(message = "Название сервиса не должно быть пустым")
    @Size(max = 200, message = "Название не должно превышать 200 символов")
    private String serviceName;
}
