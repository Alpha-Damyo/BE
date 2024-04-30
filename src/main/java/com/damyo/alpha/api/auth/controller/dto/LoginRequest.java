package com.damyo.alpha.api.auth.controller.dto;

import jakarta.validation.constraints.Email;

public record LoginRequest (
        @Email String email
) {

}
