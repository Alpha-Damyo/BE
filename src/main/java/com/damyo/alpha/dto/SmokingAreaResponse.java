package com.damyo.alpha.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SmokingAreaResponse(
        String areaId,
        String name,
        Float latitude,
        Float longitude,
        String address,
        LocalDateTime createdAt,
        boolean status,
        String description

) {
}
