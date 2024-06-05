package com.damyo.alpha.api.picture.controller.dto;

import java.util.List;

public record AllRankResponse(
        List<LikesRankResponse> topRankResponse,
        List<LikesRankResponse> nearRankResponse
) {
}
