package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;

public record LoginRequest (
        @Email String email
) {

}
