package com.damyo.alpha.api.picture.controller.dto;

import java.util.List;

public record PictureSliceResponse(
        Long lastCursorId,
        Boolean hasNest,
        List<ContestResponse> pictureList
) {
}
