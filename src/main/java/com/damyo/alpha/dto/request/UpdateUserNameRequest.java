package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record UpdateUserNameRequest (
        @Email String email,
        String name
) {

}
