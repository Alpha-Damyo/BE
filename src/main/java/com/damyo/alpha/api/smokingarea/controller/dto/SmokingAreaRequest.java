package com.damyo.alpha.api.smokingarea.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SmokingAreaRequest(
        String address,
        String name,
        String description,
        BigDecimal latitude,
        BigDecimal longitude,
        Float score,
        Boolean opened,
        Boolean closed,
        Boolean indoor,
        Boolean outdoor,
        String url
) {
}
