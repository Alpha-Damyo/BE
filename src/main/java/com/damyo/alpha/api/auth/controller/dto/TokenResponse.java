package com.damyo.alpha.api.auth.controller.dto;

import com.damyo.alpha.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "로그인과 회원가입 성공 시 반환되는 토큰 DTO")
public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
