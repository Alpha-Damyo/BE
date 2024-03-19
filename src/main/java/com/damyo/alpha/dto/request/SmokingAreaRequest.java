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
        float score,
        boolean opened,
        boolean closed,
        boolean hygiene,
        boolean dirty,
        boolean air_out
) {
}
