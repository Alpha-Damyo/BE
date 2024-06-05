package com.damyo.alpha.api.picture.controller.dto;

import java.util.UUID;

public record LikesRankResponse(
        UUID userId,
        String name,
        String profileUrl,
        Long likeCount,
        Long ranking
) {
}
