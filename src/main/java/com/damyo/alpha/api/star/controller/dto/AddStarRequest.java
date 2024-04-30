package com.damyo.alpha.api.star.controller.dto;

public record AddStarRequest (
        String saId,
        String customName,
        String groupName
) {
}
