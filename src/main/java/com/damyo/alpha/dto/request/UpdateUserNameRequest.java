package com.damyo.alpha.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class UpdateUserNameRequest {
    @Email
    private String email;
    @NotBlank
    private String name;
}
