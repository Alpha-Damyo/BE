package com.damyo.alpha.api.smokingarea.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SmokingAreaDetailResponse(
        String areaId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        LocalDateTime createdAt,
        Boolean status,
        String description,
        Float score,
        Boolean opened,
        Boolean closed,
        Boolean hygiene,
        Boolean dirty,
        Boolean airOut,
        Boolean noExist,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair
) {
}
