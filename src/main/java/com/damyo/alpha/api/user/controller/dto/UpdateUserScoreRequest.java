package com.damyo.alpha.api.user.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
public record UpdateUserScoreRequest (
        @Email String email,
        Integer increment
) {

}
