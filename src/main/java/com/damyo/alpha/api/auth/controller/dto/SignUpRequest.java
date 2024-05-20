package com.damyo.alpha.api.auth.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest (
        @Email String email,
        @NotBlank String name,
        String profileUrl,
        String gender,
        int age
) {

}

