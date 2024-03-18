package com.damyo.alpha.dto.response;

import  com.damyo.alpha.domain.User;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse (
        UUID id,
        String name,
        @Email String email,
        LocalDateTime createdAt,
        String profileUrl,
        int contribution,
        String gender,
        int age
) {
    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                user.getProfileUrl(), user.getContribution(), user.getGender(), user.getAge());
    }
}
