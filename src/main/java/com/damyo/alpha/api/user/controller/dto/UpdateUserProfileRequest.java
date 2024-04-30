package com.damyo.alpha.api.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record UpdateUserProfileRequest (
        @Email String email,
        String profileUrl
) {

}
