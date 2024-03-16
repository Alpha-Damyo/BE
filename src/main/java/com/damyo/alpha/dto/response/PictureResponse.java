package com.damyo.alpha.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PictureResponse {
    private Long id;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private int likes;
}
