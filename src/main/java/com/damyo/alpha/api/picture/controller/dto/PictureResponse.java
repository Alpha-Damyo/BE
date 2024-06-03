package com.damyo.alpha.api.picture.controller.dto;

import com.damyo.alpha.api.picture.domain.Picture;

import java.time.LocalDateTime;
public record PictureResponse (
        Long id,
        String pictureUrl,
        LocalDateTime createdAt,
        Integer likes
) {
    public PictureResponse(Picture picture) {
        this(picture.getId(), picture.getPictureUrl(), picture.getCreatedAt(), picture.getLikes());
    }
}
