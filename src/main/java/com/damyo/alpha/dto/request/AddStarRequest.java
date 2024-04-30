package com.damyo.alpha.dto.request;

public record AddStarRequest (
        String saId,
        String customName,
        String groupName
) {
}
