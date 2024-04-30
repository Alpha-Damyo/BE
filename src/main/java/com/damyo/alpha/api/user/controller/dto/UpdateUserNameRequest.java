package com.damyo.alpha.api.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record UpdateUserNameRequest (
        @Email String email,
        String name
) {

}
