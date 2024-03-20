package com.damyo.alpha.dto.request;

import java.util.List;

public record SmokingAreaListRequest(
        List<SmokingAreaRequest> areaRequests
) {
}
