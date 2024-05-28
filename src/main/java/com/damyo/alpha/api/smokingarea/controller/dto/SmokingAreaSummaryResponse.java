package com.damyo.alpha.api.smokingarea.controller.dto;

import java.math.BigDecimal;

public record SmokingAreaSummaryResponse(
        String areaId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String description,
        Float score
) {
}
