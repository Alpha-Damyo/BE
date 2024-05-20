package com.damyo.alpha.api.user.controller.dto;

import com.damyo.alpha.api.user.domain.User;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse (
        UUID id,
        String name,
        @Email String email,
        LocalDateTime createdAt,
        String profileUrl,
        Integer contribution,
        String gender,
        Integer age
) {
    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                user.getProfileUrl(), user.getContribution(), user.getGender(), user.getAge());
    }
}
