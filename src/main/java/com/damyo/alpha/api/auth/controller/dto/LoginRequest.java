package com.damyo.alpha.api.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "로그인 시 요청으로 오는 DTO")
public record LoginRequest (
        @Schema(description = "소셜 로그인 후 인증서버에서 받아오는 이메일", example = "waiting@gmail.com")
        @Email String email
) {

}
