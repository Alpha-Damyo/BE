package com.damyo.alpha.api.smokingarea.controller.dto;

import com.damyo.alpha.api.picture.controller.dto.PictureResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record SmokingAreaAllResponse(
        String areaId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        LocalDateTime createdAt,
        String description,
        Float score,
        Boolean status,
        Boolean opened,
        Long openedCount,
        Boolean closed,
        Long closedCount,
        Boolean indoor,
        Long indoorCount,
        Boolean outdoor,
        Long outdoorCount,
        List<PictureResponse> pictureList
) {
}
