package com.damyo.alpha.api.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원가입 시 요청으로 오는 DTO")
public record SignUpRequest (
        @Schema(description = "소셜 로그인 후 인증서버에서 받아오는 이메일", example = "waiting@gmail.com")
        @Email String email,
        @Schema(description = "사용자의 이름, 실명은 아니고 서비스에서 사용할 이름", example = "홍길동")
        @NotBlank String name,
        @Schema(description = "사용자의 성별", example = "남자")
        String gender,
        @Schema(description = "사용자의 나이", example = "33")
        int age
) {

}

