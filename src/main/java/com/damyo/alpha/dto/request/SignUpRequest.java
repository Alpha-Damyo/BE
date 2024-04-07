package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record SignUpRequest (
        @Email String email,
        @NotBlank String name,
        String profileUrl,
        String gender,
        int age
) {

}

