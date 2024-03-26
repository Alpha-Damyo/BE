package com.damyo.alpha.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SmokingAreaResponse(
        String areaId,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        LocalDateTime createdAt,
        boolean status,
        String description,
        float score,
        boolean opened,
        boolean closed,
        boolean hygiene,
        boolean dirty,
        boolean air_out,
        boolean no_exist,
        boolean indoor,
        boolean outdoor,
        boolean big,
        boolean small,
        boolean crowded,
        boolean quite,
        boolean chair
) {
}
