package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public record LoginRequest (
        @Email String email
) {

}
