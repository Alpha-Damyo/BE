package com.damyo.alpha.api.picture.controller.dto;

import com.damyo.alpha.api.picture.domain.Picture;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
public record PictureResponse (
        Long id,
        String pictureUrl,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime createdAt,
        Long likes
) {
    public PictureResponse(Picture picture) {
        this(picture.getId(), picture.getPictureUrl(), picture.getCreatedAt(), picture.getLikes());
    }
}
