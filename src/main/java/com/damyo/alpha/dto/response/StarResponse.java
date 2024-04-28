package com.damyo.alpha.dto.response;

import com.damyo.alpha.domain.Star;

public record StarResponse(
        Long id,
        String saId,
        String customName,
        String groupName
) {
    public StarResponse(Star star) {
        this(star.getId(), star.getSmokingArea().getId(), star.getCustomName(), star.getCustomName());
    }
}
