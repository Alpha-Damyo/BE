package com.damyo.alpha.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SmokingAreaRequest(
        String address,
        String name,
        LocalDateTime createdAt,
        String description,
        BigDecimal latitude,
        BigDecimal longitude,
        Float score,
        Boolean opened,
        Boolean closed,
        Boolean hygiene,
        Boolean dirty,
        Boolean airOut,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair
) {
}
