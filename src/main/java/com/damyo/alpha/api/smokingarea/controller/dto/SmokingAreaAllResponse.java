package com.damyo.alpha.api.smokingarea.controller.dto;

import com.damyo.alpha.api.picture.controller.dto.PictureResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        Map<Boolean, Long> opened,
        Map<Boolean, Long> closed,
        Map<Boolean, Long> hygiene,
        Map<Boolean, Long> dirty,
        Map<Boolean, Long> air_out,
        Map<Boolean, Long> no_exist,
        Map<Boolean, Long> indoor,
        Map<Boolean, Long> outdoor,
        Map<Boolean, Long> big,
        Map<Boolean, Long> small,
        Map<Boolean, Long> crowded,
        Map<Boolean, Long> quite,
        Map<Boolean, Long> chair,
        List<PictureResponse> pictureList
) {
}
