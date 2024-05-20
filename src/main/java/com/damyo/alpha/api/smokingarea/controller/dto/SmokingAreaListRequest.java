package com.damyo.alpha.api.smokingarea.controller.dto;

import java.util.List;

public record SmokingAreaListRequest(
        List<SmokingAreaRequest> areaRequests
) {
}
