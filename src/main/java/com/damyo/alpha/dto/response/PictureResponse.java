package com.damyo.alpha.dto.response;

import com.damyo.alpha.domain.Picture;
import lombok.Builder;

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
