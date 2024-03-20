package com.damyo.alpha.dto.request;

import java.util.List;

public record SmokingDataListRequest(
        List<SmokingDataRequest> dataRequests
) {
}
