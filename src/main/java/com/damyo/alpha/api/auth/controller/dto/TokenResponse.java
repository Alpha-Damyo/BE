package com.damyo.alpha.api.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인과 회원가입 성공 시 반환되는 토큰 DTO")
public record TokenResponse(
    String token
) {
}
