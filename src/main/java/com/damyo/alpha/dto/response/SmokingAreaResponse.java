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
        Boolean opened,
        Boolean closed,
        Boolean hygiene,
        Boolean dirty,
        Boolean air_out,
        Boolean no_exist,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair
) {
}
