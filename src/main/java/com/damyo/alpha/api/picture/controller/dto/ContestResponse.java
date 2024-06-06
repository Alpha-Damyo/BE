package com.damyo.alpha.api.picture.controller.dto;

import com.damyo.alpha.api.picture.domain.Picture;

import java.time.LocalDateTime;

public record ContestResponse(
        Long id,
        String pictureUrl,
        LocalDateTime createdAt,
        Long likes,
        Boolean likeCheck
) {
}
