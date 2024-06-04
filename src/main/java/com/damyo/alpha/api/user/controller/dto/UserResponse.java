package com.damyo.alpha.api.user.controller.dto;

import com.damyo.alpha.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;
@Schema(description = "유저 정보를 반환하는 DTO")
public record UserResponse (
        UUID id,
        @Schema(example = "홍길동")
        String name,
        @Schema(example = "gildong@gmail.com")
        @Email String email,
        LocalDateTime createdAt,
        @Schema(example = "profile-url")
        String profileUrl,
        @Schema(example = "43")
        int contribution,
        @Schema(example = "남자")
        String gender,
        @Schema(example = "23")
        int age,
        @Schema(example = "34.2", description = "유저의 기여도 상위 백분위")
        float percentage,
        @Schema(example = "40", description = "기여도 1등과의 기여도 차이")
        int gap

) {
    public UserResponse(User user, float percentage, int gap) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                user.getProfileUrl(), user.getContribution(), user.getGender(), user.getAge(), percentage, gap);
    }
}
