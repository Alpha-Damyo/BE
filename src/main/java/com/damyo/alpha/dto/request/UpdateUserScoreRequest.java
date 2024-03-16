package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UpdateUserScoreRequest {
    @Email
    private String email;
    private int increment;
}
