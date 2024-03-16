package com.damyo.alpha.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private String profileUrl;
    private int contribution;
    private String gender;
    private int age;
}
