package com.damyo.alpha.dto.request;

import com.damyo.alpha.dto.response.SmokingAreaResponse;

import java.util.List;

public record SmokingAreasRequest(
        List<SmokingAreaRequest> areaRequests
) {
}
