package com.damyo.alpha.dto.request;

import com.damyo.alpha.dto.request.SmokingDataRequest;

import java.util.List;

public record SmokingDatasRequest(
        List<SmokingDataRequest> dataRequests
) {
}
