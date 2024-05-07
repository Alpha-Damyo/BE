package com.damyo.alpha.api.star.controller.dto;

import com.damyo.alpha.api.star.domain.Star;

public record StarResponse(
        Long id,
        String saId,
        String customName,
        String groupName
) {
    public StarResponse(Star star) {
        this(star.getId(), star.getSmokingArea().getId(), star.getCustomName(), star.getGroupName());
    }
}
