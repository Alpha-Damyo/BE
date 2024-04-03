package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
public record UpdateUserScoreRequest (
        @Email String email,
        Integer increment
) {

}
